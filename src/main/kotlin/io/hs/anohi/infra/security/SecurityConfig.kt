package io.hs.anohi.infra.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,  //@Secured
    jsr250Enabled = true,   //@RolesAllowed
    prePostEnabled = true   //@PreAuthorize
)
class SecurityConfig(
    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun jwtAuthenticationFilter() = JwtAuthenticationFilter()


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { http ->
                run {
                    http.requestMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                    ).permitAll()
                    http.requestMatchers(HttpMethod.GET, "/swagger*/**", "/v3/api-docs").permitAll()
                    http.requestMatchers(HttpMethod.GET, "/health").permitAll()
                    http.requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
                    http.requestMatchers(HttpMethod.PATCH, "/v1/auth/token").permitAll()
                    http.requestMatchers("/ws/**").permitAll()
                    http.anyRequest().authenticated()
                }
            }
            .exceptionHandling { exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint) }
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }

}