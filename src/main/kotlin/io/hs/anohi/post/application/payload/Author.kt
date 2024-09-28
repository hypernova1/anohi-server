package io.hs.anohi.post.application.payload

import io.swagger.annotations.ApiModelProperty

data class Author(
    @field:ApiModelProperty("작성자 아이디")
    val id: Long,
    @field:ApiModelProperty("작성자 프로필 이미지")
    val image: ImageDto?
)