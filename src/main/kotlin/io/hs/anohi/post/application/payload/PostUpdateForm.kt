package io.hs.anohi.post.application.payload

import io.swagger.annotations.ApiModelProperty

class PostUpdateForm {

    @field:ApiModelProperty("제목", example = "안녕하세요.")
    val title: String? = null

    @field:ApiModelProperty("내용", example = "반갑습니다.")
    val content: String? = null

    @field:ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]")
    val tags: List<String>? = null

    @field:ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "1")
    val emotionId: Long? = null

    @field:ApiModelProperty("이미지 주소 목록", dataType = "List", example = "[1, 2, 3]")
    val images: List<ImageDto>? = null

}