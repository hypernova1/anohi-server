package io.hs.anohi.account.application.payload

import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank

data class ExistsEmailRequest (
    @ApiModelProperty("중복 여부", example = "342342342342342", required = true)
    @field:NotBlank
    var uid: String
)