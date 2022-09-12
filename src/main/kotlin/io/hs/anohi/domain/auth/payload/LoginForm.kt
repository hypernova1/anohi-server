package io.hs.anohi.domain.auth.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class LoginForm {

    @ApiModelProperty(value = "이메일", example = "hello@naver.com", required = true)
    @NotBlank(message = "이메일은 필수입니다.")
    var email: String = ""

    @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
    @NotEmpty(message = "비밀번호는 필수입니다.")
    var password: String = ""
}