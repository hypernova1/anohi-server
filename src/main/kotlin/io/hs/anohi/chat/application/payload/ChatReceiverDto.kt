package io.hs.anohi.chat.application.payload

import io.hs.anohi.account.domain.Account
import io.hs.anohi.post.application.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

data class ChatReceiverDto(
    @ApiModelProperty("유저 아이디")
    val accountId: Long,
//    @ApiModelProperty("유저명")
//    val name: String,
//    @ApiModelProperty("프로필 이미지")
//    var image: ImageDto?,
//    @ApiModelProperty("탈퇴 여부")
//    var isSecession: Boolean,
) {
//    constructor(account: Account): this(account.id, account.name, null, account.deletedAt != null) {
//        if (account.images.isNotEmpty()) {
//            this.image = ImageDto(account.images[0])
//        }
//    }
}