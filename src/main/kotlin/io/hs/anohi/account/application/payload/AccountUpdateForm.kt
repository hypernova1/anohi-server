package io.hs.anohi.account.application.payload

import io.hs.anohi.post.application.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class AccountUpdateForm(
    @field:ApiModelProperty("닉네임", example = "멜코르")
    val name: String?,
    @field:ApiModelProperty("프로필 이미지 주소", example = "")
    val image: ImageDto?,
    @field:ApiModelProperty("본인 설명", example = "")
    val description: String?,
)