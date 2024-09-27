package io.hs.anohi.auth

import com.google.api.client.auth.oauth2.TokenResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["인증"])
@RestController
@RequestMapping("/v1/auth")
class AuthController {

    @ApiOperation("JWT 토큰 검증")
    @PostMapping("/verify")
    fun verifyToken(): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok().build()
    }

}