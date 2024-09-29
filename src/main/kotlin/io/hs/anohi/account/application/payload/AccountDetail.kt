package io.hs.anohi.account.application.payload

import io.hs.anohi.account.domain.Account
import io.hs.anohi.account.domain.SocialType
import io.hs.anohi.post.application.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class AccountDetail(
    @field:ApiModelProperty("인덱스", example = "1")
    val id: Long,

    @field:ApiModelProperty("이름", example = "멜코르")
    val name: String,

    @field:ApiModelProperty("프로필 이미지", example = "https://www.naver.com")
    val image: ImageDto?,

    @field:ApiModelProperty("배경 이미지", example = "https://www.naver.com")
    val backgroundImage: ImageDto?,

    @field:ApiModelProperty("설명", example = "안녕하세요")
    val description: String,

    @field:ApiModelProperty("게시글 개수", example = "1")
    val numberOfPosts: Int = 0,

    @field:ApiModelProperty("방문자 수", example = "1")
    val numberOfVisitors: Int = 0,

    @field:ApiModelProperty("좋아요 글 수", example = "1")
    val numberOfLikes: Int = 0,

    @field:ApiModelProperty("가입일")
    val createdAt: String,

    @field:ApiModelProperty("로그인 타입")
    val socialType: SocialType,
) {
    constructor(account: Account, numberOfPosts: Int, numberOfLikes: Int) : this(
        id = account.id,
        name = account.name,
        description = account.description,
        numberOfPosts = numberOfPosts,
        numberOfVisitors = account.numberOfVisitors,
        numberOfLikes = numberOfLikes,
        createdAt = account.createdAt.toString(),
        socialType = account.socialType,
        image = if (account.images.isNotEmpty()) {
            ImageDto(account.images[0].image)
        } else {
            null
        },
        backgroundImage = null,
    )
}