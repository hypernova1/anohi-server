package io.hs.anohi.infra.security

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Transactional
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var firebaseAuth: FirebaseAuth;

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7, bearerToken.length)
        }
        return null
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = this.getJwtFromRequest(request)
            if (StringUtils.hasText(jwt)) {
                val verifyIdToken = firebaseAuth.verifyIdToken(jwt)

                val userDetails = userDetailsService.loadUserByUsername(verifyIdToken.uid)

                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            logger.error("Could not set user authentication in security context, $e")
            throw UnauthorizedException(ErrorCode.INVALID_TOKEN)
        }
        filterChain.doFilter(request, response)
    }
}