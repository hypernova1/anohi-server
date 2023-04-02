package io.hs.anohi.domain.account.payload

import io.hs.anohi.domain.account.Account
import io.swagger.annotations.ApiModelProperty

class AccountDetail(
    @ApiModelProperty("인덱스", example = "1")
    var id: Long,

    @ApiModelProperty("이메일", example = "hypemova@gmail.com")
    var email: String,

    @ApiModelProperty("이름", example = "멜코르")
    var name: String,

    @ApiModelProperty("설명", example = "안녕하세요")
    var description: String,

    @ApiModelProperty("게시글 개수", example = "1")
    var numberOfPosts: Int = 0,

    @ApiModelProperty("방문자 수", example = "1")
    var numberOfVisitors: Int = 0,

    @ApiModelProperty("좋아요 글 수", example = "1")
    var numberOfLikes: Int = 0,
) {
    constructor(account: Account, numberOfPosts: Int, numberOfLikes: Int) : this(0, "", "", "") {
        this.id = account.id
        this.email = account.email
        this.name = account.name
        this.description = account.description
        this.numberOfPosts = numberOfPosts
        this.numberOfVisitors = account.numberOfVisitors
        this.numberOfLikes = numberOfLikes
    }
}