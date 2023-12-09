package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.ImageDto

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