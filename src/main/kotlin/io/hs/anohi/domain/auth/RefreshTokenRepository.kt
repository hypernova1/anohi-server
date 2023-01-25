package io.hs.anohi.domain.auth

import io.hs.anohi.domain.auth.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun existsByAccountEmail(email: String): Boolean
}