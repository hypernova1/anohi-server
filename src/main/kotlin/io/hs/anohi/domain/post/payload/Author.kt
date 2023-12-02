package io.hs.anohi.domain.post.payload

import io.swagger.annotations.ApiModelProperty

data class Author(
    @ApiModelProperty("작성자 아이디")
    var id: Long,
    @ApiModelProperty("작성자 프로필 이미지")
    var image: ImageDto?
)