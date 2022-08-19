package io.hs.anohi.domain.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun existsByAccountEmail(email: String): Boolean
}