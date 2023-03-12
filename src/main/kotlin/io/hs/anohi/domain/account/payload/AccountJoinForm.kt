package io.hs.anohi.domain.account.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class AccountJoinForm(

    @ApiModelProperty("이메일", example = "hello@naver.com", required = true)
    @field:Email(message = "이메일 형식이 아닙니다.")
    @field:NotBlank
    val email: String,

    @ApiModelProperty("이름", example = "김민호", required = true)
    @field:NotBlank
    val name: String,

    @ApiModelProperty("비밀번호", example = "1234", required = true)
    @field:NotBlank
    @field:Size(min = 4, max = 20)
    var password: String,
)