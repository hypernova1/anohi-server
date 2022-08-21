package io.hs.anohi.domain.account.payload

import javax.validation.constraints.NotBlank

class AccountUpdateForm(

    @field:NotBlank
    val name: String,
    @field:NotBlank
    var password: String,
)