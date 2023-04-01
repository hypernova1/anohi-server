package io.hs.anohi.domain.account.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class AccountUpdateForm(
    @ApiModelProperty("닉네임", example = "멜코르", required = false)
    val nickname: String?,
    @ApiModelProperty("비밀번호", example = "192912192", required = false)
    var password: String?,
    @ApiModelProperty("프로필 이미지 주소", example = "", required = false)
    var profileImagePath: String?,
    @ApiModelProperty("본인 설명", example = "", required = false)
    var description: String?,
)