package io.hs.anohi.domain.auth

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.core.exception.UnauthorizedException
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.account.AccountService
import io.hs.anohi.domain.auth.payload.TokenRequest
import io.hs.anohi.domain.auth.payload.TokenResponse
import io.hs.anohi.infra.security.CustomUserDetailsService
import io.hs.anohi.infra.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class AuthService(
    val accountRepository: AccountRepository,
    val refreshTokenRepository: RefreshTokenRepository,
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
    val accountService: AccountService,
    val userDetailsService: CustomUserDetailsService,
    val firebaseAuth: FirebaseAuth,
) {

    @Transactional
    fun login(token: String): TokenResponse {
        val decodedToken = this.firebaseAuth.verifyIdToken(token.split(" ")[1])


        val existsUid = accountRepository.existsByUid(decodedToken.uid)

        if (!existsUid) {
            accountService.create(decodedToken.email, decodedToken.name, decodedToken.picture)
        }

        val userDetails = userDetailsService.loadUserByUsername(decodedToken.uid)

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken = jwtTokenProvider.generateToken(authentication.name, authentication.authorities)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication.name, authentication.authorities)


        return TokenResponse(accessToken, refreshToken)
    }

    @Transactional
    fun reissueToken(request: TokenRequest): TokenResponse {
        val existsRefreshToken = this.refreshTokenRepository.existsByAccountEmail(request.email)
        if (!existsRefreshToken) {
            throw UnauthorizedException(ErrorCode.CANNOT_FOUND_REFRESH_TOKEN)
        }

        val isValid = jwtTokenProvider.validationToken(request.refreshToken)
        if (!isValid) {
            throw UnauthorizedException(ErrorCode.INVALID_TOKEN)
        }

        val account = accountRepository.findByUid(request.email)
            .orElseThrow { UnauthorizedException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

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