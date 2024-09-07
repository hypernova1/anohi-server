package io.hs.anohi.account.ui.payload

import io.swagger.annotations.ApiModelProperty

class AccountSummary(
    @ApiModelProperty("인덱스", example = "1")
    val id: Long,

    @ApiModelProperty("이메일", example = "hypemova@gmail.com")
    val email: String,

    @ApiModelProperty("이름", example = "멜코르")
    val name: String,
)