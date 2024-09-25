package io.hs.anohi.infra.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class JwtTokenProvider(
    @Value("\${key.jwtSecret}")
    private var jwtSecret: String? = null,
) {

    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java.name)

    fun generateToken(email: String, authorities: MutableCollection<out GrantedAuthority>): String {
        val tokenInvalidTime = 1000L * 60 * 60 * 24 * 30
        return createToken(email, tokenInvalidTime, authorities)
    }

    fun generateRefreshToken(email: String, authorities: MutableCollection<out GrantedAuthority>): String {
        val tokenInvalidTime: Long = 1000L * 60 * 60 * 24 * 90
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

}