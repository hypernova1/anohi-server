package io.hs.anohi.infra.filter

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class LoggingFilter: OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java.name)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        filterChain.doFilter(request, response)
    }

}

