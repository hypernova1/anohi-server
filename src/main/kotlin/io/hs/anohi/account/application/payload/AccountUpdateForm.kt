package io.hs.anohi.account.ui.payload

import io.hs.anohi.post.ui.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class AccountUpdateForm(
    @ApiModelProperty("닉네임", example = "멜코르")
    val name: String?,
    @ApiModelProperty("프로필 이미지 주소", example = "")
    var image: ImageDto?,
    @ApiModelProperty("본인 설명", example = "")
    var description: String?,
)