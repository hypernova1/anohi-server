package io.hs.anohi.post.ui.payload

import io.hs.anohi.domain.post.entity.Image

data class ImageDto(
    val id: Long?,
    val path: String,
    val width: Int?,
    val height: Int?,
    val blurHash: String?
) {
    constructor(image: Image) : this(image.id, image.originUrl, image.width, image.height, image.blurHash)
}