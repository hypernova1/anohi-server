package io.hs.anohi.post.application.payload

import io.hs.anohi.post.application.payload.ImageDto
import io.swagger.annotations.ApiModelProperty

class PostUpdateForm {

    @ApiModelProperty("제목", example = "안녕하세요.")
    var title: String? = null

    @ApiModelProperty("내용", example = "반갑습니다.")
    var content: String? = null

    @ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]")
    var tags: List<String>? = null

    @ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "1")
    var emotionId: Long? = null

    @ApiModelProperty("이미지 주소 목록", dataType = "List", example = "[1, 2, 3]")
    val images: List<ImageDto>? = null

}