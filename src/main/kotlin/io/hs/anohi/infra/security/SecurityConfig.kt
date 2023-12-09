package io.hs.anohi.infra.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,  //@Secured
    jsr250Enabled = true,   //@RolesAllowed
    prePostEnabled = true   //@PreAuthorize
)
class SecurityConfig(
    @Autowired
    private val customUserDetailService: CustomUserDetailsService,
    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    companion object {
        const val MAX_AGE_SECS = 3600L
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun jwtAuthenticationFilter() = JwtAuthenticationFilter()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()


    override fun configure(auth: AuthenticationManagerBuilder?) {
        if (auth == null) return
        auth
            .userDetailsService(customUserDetailService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun corsConfiguration(): CorsConfiguration {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("HEAD", "OPTION", "GET", "POST", "PUT", "PATCH", "DELETE")
        configuration.allowedHeaders = listOf("*")
        configuration.maxAge = MAX_AGE_SECS
        configuration.allowCredentials = true

        return configuration
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedOrigin("*")
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        source.registerCorsConfiguration("/v1/notifications/subscribe", config) // CORS를 적용할 패턴 설정
        return CorsFilter(source)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()

            .antMatchers(
                "/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
            )
            .permitAll()
            .antMatchers(HttpMethod.GET, "/swagger*/**", "/v3/api-docs").permitAll()
            .antMatchers(HttpMethod.GET, "/health").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
            .antMatchers(HttpMethod.PATCH, "/v1/auth/token").permitAll()
            .antMatchers("/ws/**").permitAll()
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()

        http.csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

}