package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty

class DiaryUpdateForm {

    @ApiModelProperty("제목", example = "안녕하세요.")
    var title: String? = null

    @ApiModelProperty("내용", example = "반갑습니다.")
    var content: String? = null

    @ApiModelProperty("내용", required = true, dataType = "List", example = "[1, 2, 3]")
    var categoryIds: List<Long>? = null

    @ApiModelProperty("태그 목록",  dataType = "List", example = "[\"str1\", \"str2\", \"str3\"]")
    var tags: List<String>? = null


    @ApiModelProperty("감정 인덱스 목록", dataType = "List", example = "[1, 2, 3]")
    var emotionIds: List<Long>? = null
}