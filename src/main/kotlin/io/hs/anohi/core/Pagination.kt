package io.hs.anohi.core

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class Pagination(
    @ApiModelProperty("페이지 번호", example = "1", required = false)
    val page: Int,

    @ApiModelProperty("페이지 크기", example = "20", required = true)
    val size: Int,
)