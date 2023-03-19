package io.hs.anohi.domain.auth.payload

import io.hs.anohi.domain.auth.constant.SocialType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel
class LoginForm {

    @ApiModelProperty("소셜 타입", example = "GOOGLE", required = true)
    var socialType: SocialType = SocialType.GOOGLE

    @ApiModelProperty("토큰", example = "hello@naver.com", required = true)
    @NotBlank(message = "토큰은 필수입니다.")
    var token: String = ""



}