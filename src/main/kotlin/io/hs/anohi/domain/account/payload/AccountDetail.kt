package io.hs.anohi.domain.account.payload

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class AccountDetail(
    @ApiModelProperty("인덱스", example = "1")
    var id: Long,

    @ApiModelProperty("이름", example = "멜코르")
    var name: String,

    @ApiModelProperty("프로필 이미지", example = "https://www.naver.com")
    var image: ImageDto?,

    @ApiModelProperty("배경 이미지", example = "https://www.naver.com")
    var backgroundImage: ImageDto?,

    @ApiModelProperty("설명", example = "안녕하세요")
    var description: String,

    @ApiModelProperty("게시글 개수", example = "1")
    var numberOfPosts: Int = 0,

    @ApiModelProperty("방문자 수", example = "1")
    var numberOfVisitors: Int = 0,

    @ApiModelProperty("좋아요 글 수", example = "1")
    var numberOfLikes: Int = 0,
) {
    constructor(account: Account, numberOfPosts: Int, numberOfLikes: Int) : this(
        account.id,
        account.name,
        null,
        null,
        account.description,
        numberOfPosts,
        account.numberOfVisitors,
        numberOfLikes
    ) {
        if (account.images.isNotEmpty()) {
            this.image = ImageDto(account.images[0])
        }
    }
}