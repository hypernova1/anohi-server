package io.hs.anohi.domain.auth

import io.hs.anohi.domain.auth.payload.LoginForm
import io.hs.anohi.domain.auth.payload.TokenRequest
import io.hs.anohi.domain.auth.payload.TokenResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api(tags = ["인증"])
@RestController
@RequestMapping("/v1/auth")
class AuthController(private val authService: AuthService) {

    @ApiOperation("로그인")
    @PostMapping
    fun login(@RequestHeader("Authorization") token: String): ResponseEntity<TokenResponse> {
        val response = authService.login(token)
        return ResponseEntity.ok(response)
    }

    @ApiOperation("JWT 재발급")
    @PatchMapping("/token")
    fun reissueToken(@Valid @RequestBody refreshToken: TokenRequest): ResponseEntity<TokenResponse> {
        val token = authService.reissueToken(refreshToken)
        return ResponseEntity.ok(token)
    }

    @ApiOperation("JWT 토큰 검증")
    @PostMapping("/verify")
    fun verifyToken(): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok().build()
    }

}