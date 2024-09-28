package io.hs.anohi.account.application.payload

import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank

data class ExistsEmailRequest (
    @field:ApiModelProperty("중복 여부", example = "342342342342342", required = true)
    @field:NotBlank
    val uid: String
)