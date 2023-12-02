package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

data class ChatReceiverDto(
    @ApiModelProperty("유저 아이디")
    val id: Long,
    @ApiModelProperty("유저명")
    val name: String,
    @ApiModelProperty("프로필 이미지")
    var image: ImageDto?,
) {
    constructor(account: Account): this(account.id, account.name, null) {
        if (account.images.isNotEmpty()) {
            this.image = ImageDto(account.images[0])
        }
    }
}