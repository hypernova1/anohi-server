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
    var ImageUrls: List<String>,

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
        post.images.map { it.originUrl },
        Author(post.account.id, post.account.name, post.account.profileImageUrl),
        post.createdAt.toString(),
        post.updatedAt.toString(),
        post.hit
    )
}