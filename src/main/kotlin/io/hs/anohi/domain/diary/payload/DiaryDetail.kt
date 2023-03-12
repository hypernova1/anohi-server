package io.hs.anohi.domain.diary.payload

import io.hs.anohi.domain.diary.entity.Diary
import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class DiaryDetail(
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

    var tags: List<String>,

    var emotions: List<String>,

    var categories: List<String>,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val createdAt: String,

    @ApiModelProperty("생성일")
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss")
    val updatedAt: String
) {
    constructor(diary: Diary) : this(diary.id, diary.title, diary.content,  emptyList(), emptyList(), emptyList(), diary.createdDate.toString(), diary.updatedDate.toString()) {
        this.categories = diary.categories.map { it.name }
        this.tags = diary.tags.map { it.name }
        this.emotions = diary.emotions.map { it.name }
    }
}