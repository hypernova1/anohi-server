package io.hs.anohi.domain.auth

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.domain.auth.payload.LoginForm
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.auth.payload.TokenRequest
import io.hs.anohi.domain.auth.payload.TokenResponse
import io.hs.anohi.infra.exception.NotFoundException
import io.hs.anohi.infra.exception.UnauthorizedException
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
    fun countByEmail(email: String) = accountRepository.countByEmail(email)

    fun login(loginForm: LoginForm): TokenResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginForm.email, loginForm.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val accessToken = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        val authorities = authentication.authorities
        return TokenResponse(accessToken, refreshToken)
    }

    fun reissueToken(request: TokenRequest): TokenResponse {
        val existsRefreshToken = this.refreshTokenRepository.existsByAccountEmail(request.email)
        if (!existsRefreshToken) {
            throw NotFoundException(ErrorCode.CANNOT_FOUND_REFRESH_TOKEN)
        }

        val isValid = jwtTokenProvider.validationToken(request.refreshToken)
        if (!isValid) {
            throw UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        val authentication = SecurityContextHolder.getContext().authentication
        val accessToken = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)

        return TokenResponse(accessToken, refreshToken)
    }
}