package io.hs.anohi.account.application.payload

import io.swagger.annotations.ApiModelProperty

data class ExistsEmailResponse(
    @ApiModelProperty("중복 여부", required = true)

    var isDuplicate: Boolean
)