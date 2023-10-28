package io.hs.anohi.domain.post.payload

data class Author(
    var id: Long,
    var name: String,
    var profileImagePath: String? = "",
)