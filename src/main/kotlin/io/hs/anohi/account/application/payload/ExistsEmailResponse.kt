package io.hs.anohi.account.application.payload

import io.swagger.annotations.ApiModelProperty

data class ExistsEmailResponse(
    @field:ApiModelProperty("중복 여부", required = true)
    val isDuplicate: Boolean
)