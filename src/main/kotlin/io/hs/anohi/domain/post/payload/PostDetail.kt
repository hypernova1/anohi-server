package io.hs.anohi.domain.post.payload

import io.hs.anohi.domain.post.entity.Post
import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat

data class PostDetail(
    @ApiModelProperty("인덱스", example = "1")
    var id: Long,

    @ApiModelProperty(
        "제목", example = "제목입니다."
    )
    var title: String,

    @ApiModelProperty(
        "내용", example = "내용입니다."
    )
    var content: String,

    @ApiModelProperty("태그 목록")
    var tags: List<String>,

    @ApiModelProperty("감정 인덱스 목록")
    var emotionId: Long?,

    @ApiModelProperty("이미지 주소 목록")
    var images: List<ImageDto>,

    @ApiModelProperty("작성자 요약")
    var author: Author?,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    var createdAt: String,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    var updatedAt: String,

    @ApiModelProperty("조회수")
    val hit: Long
) {
    constructor(post: Post) : this(
        post.id,
        post.title,
        post.content,
        post.tags.map { it.name },
        post.emotion?.id,
        post.images.map { ImageDto(it) },
        null,
        post.createdAt.toString(),
        post.updatedAt.toString(),
        post.hit
    ) {
        val authorImage = if (post.account.images.isNotEmpty()) {
            ImageDto(post.account.images[0])
        } else {
            null
        }
        this.author = Author(post.account.id, authorImage)
    }
}