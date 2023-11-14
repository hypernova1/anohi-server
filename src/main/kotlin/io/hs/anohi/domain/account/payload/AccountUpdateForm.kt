package io.hs.anohi.domain.account.payload

import io.hs.anohi.domain.post.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class AccountUpdateForm(
    @ApiModelProperty("닉네임", example = "멜코르", required = false)
    val name: String?,
    @ApiModelProperty("프로필 이미지 주소", example = "", required = false)
    var image: ImageDto?,
    @ApiModelProperty("본인 설명", example = "", required = false)
    var description: String?,
)