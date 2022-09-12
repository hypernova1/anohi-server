package io.hs.anohi.domain.auth.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class TokenRequest(

    @ApiModelProperty(value = "이메일", example = "hello@naver.com", required = true)
    @field:NotBlank
    val email: String,

    @ApiModelProperty(value = "리프레시토큰", example = "", required = true)
    @field:NotBlank
    val refreshToken: String
)