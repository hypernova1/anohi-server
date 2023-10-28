package io.hs.anohi.domain.account.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class AccountJoinForm(

    @ApiModelProperty("구글 파이어베이스 토큰", example = "1234", required = true)
    @field:NotBlank
    var token: String,
)