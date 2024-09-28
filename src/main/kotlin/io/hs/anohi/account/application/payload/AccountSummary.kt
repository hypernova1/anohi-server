package io.hs.anohi.account.application.payload

import io.swagger.annotations.ApiModelProperty

class AccountSummary(
    @field:ApiModelProperty("인덱스", example = "1")
    val id: Long,

    @field:ApiModelProperty("이메일", example = "hypemova@gmail.com")
    val email: String,

    @field:ApiModelProperty("이름", example = "멜코르")
    val name: String,
)