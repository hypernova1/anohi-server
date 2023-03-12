package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class DiaryDetail(
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

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val createdAt: LocalDateTime,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val updatedAt: LocalDateTime
)