package io.hs.anohi.post.ui.payload

import io.swagger.annotations.ApiModelProperty
import java.util.Collections
import javax.validation.constraints.NotBlank

class PostRequestForm {

    @ApiModelProperty("제목", example = "안녕하세요.", required = false)
    val title: String = ""

    @ApiModelProperty("내용", example = "반갑습니다.", required = true)
    @NotBlank
    val content: String = ""

    @ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]", required = false)
    val tags: List<String> = Collections.emptyList()


    @ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "1", required = true)
    val emotionId: Long? = null;

    @ApiModelProperty("이미지 주소 목록", dataType = "List", example = "[\"https://google.com\"]", required = false)
    val images: List<ImageDto> = Collections.emptyList()

}