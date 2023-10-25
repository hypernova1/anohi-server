package io.hs.anohi.domain.account.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ExistsEmailRequest (
    @ApiModelProperty("중복 여부", example = "342342342342342", required = true)
    @field:NotBlank
    var uid: String
)