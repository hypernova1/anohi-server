package io.hs.anohi.core

import io.swagger.annotations.ApiModelProperty

class PageRequest(
    @ApiModelProperty("페이지 번호", example = "1", required = false)
    val page: Int,

    @ApiModelProperty("페이지 크기", example = "20", required = true)
    val size: Int,
)