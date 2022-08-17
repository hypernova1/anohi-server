package io.hs.anohi.domain.account.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class AccountJoinForm(

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank
    val email: String,

    @NotBlank
    val name: String,

    @NotBlank
    @Size(min = 4, max = 20)
    var password: String,
)