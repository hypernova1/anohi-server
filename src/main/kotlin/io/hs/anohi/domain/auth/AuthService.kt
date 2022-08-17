package io.hs.anohi.domain.auth

import io.hs.anohi.domain.auth.payload.LoginForm
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.auth.payload.TokenRequest
import io.hs.anohi.domain.auth.payload.TokenResponse
import io.hs.anohi.infra.exception.RefreshTokenNotFoundException
import io.hs.anohi.infra.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val accountRepository: AccountRepository,
    val refreshTokenRepository: RefreshTokenRepository,
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
    val passwordEncoder: PasswordEncoder
) {
    fun countByEmail(email: String) = accountRepository.countByEmail(email);

    fun login(loginForm: LoginForm): TokenResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginForm.email, loginForm.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val accessToken = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        val authorities = authentication.authorities
        return TokenResponse(accessToken, refreshToken)
    }

    fun reissueToken(request: TokenRequest) {
        val refreshToken = this.refreshTokenRepository.findByAccountEmail(request.email)
            .orElseThrow { RefreshTokenNotFoundException() }

    }
}