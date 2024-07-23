package io.hs.anohi.infra.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 인증 예외 발생시 호출
 * */
@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java.name)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.error("Responding with unauthorized error. Message - ${authException?.message}")

        response?.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            jacksonObjectMapper().writeValueAsString(
                ErrorResponse<Any>(
                    ErrorCode.INVALID_TOKEN.code,
                    ErrorCode.INVALID_TOKEN.message
                )
            )
        )
    }

}