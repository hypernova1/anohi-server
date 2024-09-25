package io.hs.anohi.chat.application.payload

import io.hs.anohi.account.domain.Account
import io.hs.anohi.post.application.payload.ImageDto

data class Sender(
    val id: Long,
    val email: String,
    var image: ImageDto?
) {
    constructor(account: Account): this(account.id, account.email, null) {
        this.image = if (account.images.isNotEmpty()) {
            ImageDto(account.images[0])
        } else {
            null
        }
    }
}