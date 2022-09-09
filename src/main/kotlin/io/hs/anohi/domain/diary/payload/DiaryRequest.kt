package io.hs.anohi.domain.diary.payload

import javax.validation.constraints.NotBlank

class DiaryRequest {

    @NotBlank
    val title: String = ""

    @NotBlank
    val content: String = ""
}