package io.hs.anohi.post.application.payload

import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank
import java.util.*

class PostRequestForm {

    @field:ApiModelProperty("제목", example = "안녕하세요.", required = false)
    val title: String = ""

    @field:ApiModelProperty("내용", example = "반갑습니다.", required = true)
    @field:NotBlank
    val content: String = ""

    @field:ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]", required = false)
    val tags: List<String> = Collections.emptyList()

    @field:ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "1", required = true)
    val emotionId: Long? = null;

    @field:ApiModelProperty("이미지 주소 목록", dataType = "List", example = "[\"https://google.com\"]", required = false)
    val images: List<ImageDto> = Collections.emptyList()

}