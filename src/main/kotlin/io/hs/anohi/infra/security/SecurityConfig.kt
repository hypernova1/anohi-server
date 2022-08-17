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

    override fun configure(http: HttpSecurity) {
        http.cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .authorizeRequests()
            .antMatchers("/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/v1/accounts", "/v1/auth")
            .permitAll()
            .anyRequest()
            .hasRole("USER");
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java);
    }
}