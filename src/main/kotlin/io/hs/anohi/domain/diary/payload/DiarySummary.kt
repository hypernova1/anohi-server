package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty

class DiarySummary(

    @ApiModelProperty("인덱스", example = "1")
    val id: Long,

    @ApiModelProperty(
        "제목", example = "제목입니다."
    )
    val title: String,

    @ApiModelProperty(
        "내용", example = "내용입니다."
    )
    val content: String,
)