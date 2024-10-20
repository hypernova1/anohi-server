package io.hs.anohi.auth.payload

import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank

class TokenRequest(

    @field:ApiModelProperty("이메일", example = "hello@naver.com", required = true)
    @field:NotBlank
    val email: String,

    @field:ApiModelProperty("리프레시토큰", example = "", required = true)
    @field:NotBlank
    val refreshToken: String
)