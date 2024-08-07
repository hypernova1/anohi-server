package io.hs.anohi.account.ui.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class AccountJoinForm(

    @ApiModelProperty("구글 파이어베이스 토큰", example = "1234", required = true)
    @field:NotBlank
    var token: String,
)