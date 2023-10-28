package io.hs.anohi.infra.security

import org.slf4j.LoggerFactory
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 인증 예외 발생시 호출
 * */
@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java.name)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: org.springframework.security.core.AuthenticationException?
    ) {
        logger.error("Responding with unauthorized error. Message - ${authException?.message}")

        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException?.message)
    }

}