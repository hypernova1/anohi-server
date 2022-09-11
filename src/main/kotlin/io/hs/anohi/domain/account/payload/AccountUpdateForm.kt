package io.hs.anohi.domain.account.payload

import javax.validation.constraints.NotBlank

class AccountUpdateForm(
    val name: String?,
    var password: String?,
)