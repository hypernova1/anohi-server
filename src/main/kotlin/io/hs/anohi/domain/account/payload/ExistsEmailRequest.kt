package io.hs.anohi.domain.account.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ExistsEmailRequest (
    @ApiModelProperty("중복 여부", example = "han@kiwistud.io", required = true)
    @field:Email(message = "이메일 형식이 아닙니다.")
    @field:NotBlank
    var email: String
)