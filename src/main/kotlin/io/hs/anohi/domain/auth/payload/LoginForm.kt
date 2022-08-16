package io.hs.anohi.domain.auth.payload

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class LoginForm {
    @NotBlank(message = "이메일은 필수입니다.")
    var email: String = ""
    @NotEmpty(message = "비밀번호는 필수입니다.")
    var password: String = ""
}