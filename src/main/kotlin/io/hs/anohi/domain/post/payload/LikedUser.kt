package io.hs.anohi.domain.post.payload

data class LikedUser(
    var id: Long,
    var nickname: String,
    var profileImagePath: String? = "",
)