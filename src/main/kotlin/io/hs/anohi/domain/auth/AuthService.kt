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
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.auth.constant.RoleName
import io.hs.anohi.domain.auth.constant.SocialType
import io.hs.anohi.domain.auth.entity.RefreshToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder

@Service
@Transactional(readOnly = true)
class AuthService(
    val accountRepository: AccountRepository,
    val refreshTokenRepository: RefreshTokenRepository,
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
    val roleRepository: RoleRepository,
    val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun login(loginForm: LoginForm): TokenResponse {
        var email: String? = null
        var password: String? = null
        if (loginForm.socialType == SocialType.GOOGLE) {
            val transport = NetHttpTransport()
            val jsonFactory = GsonFactory()
            val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//            .setAudience(Collections.singletonList())
                .build()

            val idToken = verifier.verify(loginForm.token) ?: throw UnauthorizedException(ErrorCode.CANNOT_FOUND_ACCOUNT)

            val payload = idToken.payload

            password = payload.subject
            email = payload.email

            val name = payload["name"] as String?
            val pictureUrl = payload["picture"] as String?

            val existsEmail = accountRepository.existsByEmail(email)
            if (!existsEmail) {
                val hashedPassword = passwordEncoder.encode(password)
                val account = Account.from(name = name, email = email, password = hashedPassword, profileImagePath = pictureUrl)

                val role = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

                account.addRole(role)
                accountRepository.save(account)
            }
        }

        if (email == null || password == null) {
            throw UnauthorizedException(ErrorCode.CANNOT_FOUND_ACCOUNT)
        }

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken = jwtTokenProvider.generateToken(authentication.name, authentication.authorities)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication.name, authentication.authorities)

        val account = accountRepository.findByEmail(email)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        refreshTokenRepository.save(RefreshToken(refreshToken, account))

        return TokenResponse(accessToken, refreshToken)
    }

    @Transactional
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