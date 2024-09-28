package io.hs.anohi.post.application.payload

import io.hs.anohi.post.domain.Post
import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat

data class PostDetail(
    @field:ApiModelProperty("인덱스", example = "1")
    val id: Long,

    @field:ApiModelProperty(
        "제목", example = "제목입니다."
    )
    val title: String,

    @field:ApiModelProperty(
        "내용", example = "내용입니다."
    )
    val content: String,

    @field:ApiModelProperty("태그 목록")
    val tags: List<String>,

    @field:ApiModelProperty("감정 인덱스 목록")
    val emotionId: Long?,

    @field:ApiModelProperty("이미지 주소 목록")
    val images: List<ImageDto>,

    @field:ApiModelProperty("작성자 요약")
    val author: Author?,

    @field:ApiModelProperty("생성일")
    @field:DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val createdAt: String,

    @field:ApiModelProperty("생성일")
    @field:DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val updatedAt: String,

    @field:ApiModelProperty("조회수")
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
//        val authorImage = if (post.account.images.isNotEmpty()) {
//            ImageDto(post.account.images[0])
//        } else {
//            null
//        }
//        this.author = Author(post.account.id, authorImage)
    }
}