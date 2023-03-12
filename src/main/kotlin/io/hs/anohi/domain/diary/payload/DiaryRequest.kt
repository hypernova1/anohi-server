package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty
import java.util.Collections
import javax.validation.constraints.NotBlank

class DiaryRequest {

    @ApiModelProperty("제목", example = "안녕하세요.", required = true)
    @NotBlank
    val title: String = ""

    @ApiModelProperty("내용", example = "반갑습니다.", required = true)
    @NotBlank
    val content: String = ""

    @ApiModelProperty("내용", required = true, dataType = "List", example = "[1, 2, 3]")
    val categoryIds: List<Long> = Collections.emptyList()

    @ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]", required = true)
    val tags: List<String> = Collections.emptyList()


    @ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "[1, 2, 3]", required = true)
    val emotionIds: List<Long> = Collections.emptyList()

    @ApiModelProperty("이미지 주소 목록", dataType = "List", example = "[\"https://google.com\"]", required = false)
    val imagePaths: List<String> = Collections.emptyList()

}