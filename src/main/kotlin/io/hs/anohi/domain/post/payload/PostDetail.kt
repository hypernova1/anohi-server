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
    var emotionIds: List<Long>,

    @ApiModelProperty("카테고리 인덱스 목록")
    var categoryIds: List<Long>,

    @ApiModelProperty("이미지 주소 목록")
    var imagePaths: List<String>,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    var createdAt: String,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    var updatedAt: String,

    @ApiModelProperty("좋아요 누른 유저 목록")
    var likedUsers: List<LikedUser>,
) {
    constructor(post: Post) : this(post.id, post.title, post.content,  emptyList(), emptyList(), emptyList(), emptyList(), post.createdAt.toString(), post.updatedAt.toString(), emptyList()) {
        this.categoryIds = post.categories.map { it.id }
        this.tags = post.tags.map { it.name }
        this.emotionIds = post.emotions.map { it.id }
        this.imagePaths = post.images.map { it.originPath }
        this.likedUsers = post.favoritePosts.map { LikedUser(it.account.id, it.account.name, it.account.profileImagePath) }
    }
}