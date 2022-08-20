package io.hs.anohi.infra.security

import io.hs.anohi.domain.auth.RefreshTokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.Date
import java.util.LinkedList
import java.util.concurrent.TimeUnit

@Component
class JwtTokenProvider(
    @Autowired
    private val refreshTokenRepository: RefreshTokenRepository,

    @Value("\${key.jwtSecret}")
    private var jwtSecret: String? = null,
) {

    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java.name)


    fun generateToken(email: String, authorities: MutableCollection<out GrantedAuthority>): String {
        val tokenInvalidTime = 1000L * 60 * 60 * 24 * 1
        return createToken(email, tokenInvalidTime, authorities)
    }

    fun generateRefreshToken(email: String, authorities: MutableCollection<out GrantedAuthority>): String {
        val tokenInvalidTime: Long = 1000L * 60 * 60 * 24 * 3
        return this.createToken(email, tokenInvalidTime, authorities)
    }

    fun createToken(name: String, expiredTime: Long, roles: MutableCollection<out GrantedAuthority>): String {
        val claims = HashMap<String, Any>()
        claims["roles"] = roles
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key = Keys.hmacShaKeyFor(keyBytes)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(name)
            .setExpiration(Date(Date().time + TimeUnit.HOURS.toMillis(expiredTime)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAuthentication(token: String): Authentication? {
        val tokenBody = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body

        val username: String = tokenBody.subject

        @Suppress("UNCHECKED_CAST")
        val roles = tokenBody["roles"] as List<String>

        val res = roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it) }

        logger.info("$username logged in with authorities $res")
        return UsernamePasswordAuthenticationToken(username, null, res)
    }

    fun getEmailFromJwt(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parse(token)
            .body as Claims

        return claims.subject
    }

    fun validationToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty")
        }
        return false
    }

}