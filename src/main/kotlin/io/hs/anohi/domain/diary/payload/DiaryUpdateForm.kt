package io.hs.anohi.domain.diary.payload

import io.swagger.annotations.ApiModelProperty

class DiaryUpdateForm {
    
    @ApiModelProperty(value = "제목", required = false)
    val title: String? = null

    @ApiModelProperty(value = "내용", required = false)
    val content: String? = null
}