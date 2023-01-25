package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class DiaryRequest {

    @ApiModelProperty("제목", example = "안녕하세요.", required = true)
    @NotBlank
    val title: String = ""

    @ApiModelProperty("내용", example = "반갑습니다.", required = true)
    @NotBlank
    val content: String = ""
}