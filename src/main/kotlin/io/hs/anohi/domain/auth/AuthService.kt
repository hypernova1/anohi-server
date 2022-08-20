package io.hs.anohi.domain.auth

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.domain.auth.payload.LoginForm
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.auth.payload.TokenRequest
import io.hs.anohi.domain.auth.payload.TokenResponse
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.core.exception.UnauthorizedException
import io.hs.anohi.infra.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    val accountRepository: AccountRepository,
    val refreshTokenRepository: RefreshTokenRepository,
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
) {

    fun login(loginForm: LoginForm): TokenResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginForm.email, loginForm.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val accessToken = jwtTokenProvider.generateToken(authentication.name, authentication.authorities)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication.name, authentication.authorities)

        val account = accountRepository.findByEmail(loginForm.email)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        refreshTokenRepository.save(RefreshToken(refreshToken, account))

        return TokenResponse(accessToken, refreshToken)
    }

    fun reissueToken(request: TokenRequest): TokenResponse {
        val existsRefreshToken = this.refreshTokenRepository.existsByAccountEmail(request.email)
        if (!existsRefreshToken) {
            throw NotFoundException(ErrorCode.CANNOT_FOUND_REFRESH_TOKEN)
        }

        val isValid = jwtTokenProvider.validationToken(request.refreshToken)
        if (!isValid) {
            throw UnauthorizedException(ErrorCode.INVALID_TOKEN)
        }

        val account = accountRepository.findByEmail(request.email)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        val authorities = account.roles.mapTo(LinkedList<GrantedAuthority>()) {
            SimpleGrantedAuthority(it.name.toString())
        }

        val authentication = jwtTokenProvider.getAuthentication(request.refreshToken)
            ?: throw UnauthorizedException(ErrorCode.CANNOT_FOUND_ACCOUNT)

        val accessToken = jwtTokenProvider.generateToken(request.email, authentication.authorities)
        val refreshToken = jwtTokenProvider.generateRefreshToken(request.email, authorities)

        return TokenResponse(accessToken, refreshToken)
    }
}