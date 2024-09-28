package io.hs.anohi.core

import io.swagger.annotations.ApiModelProperty

open class Pagination {
    @ApiModelProperty(required = false)
    var size: Int = 10

    @ApiModelProperty(required = false)
    val lastItemId: Long = 0

    @ApiModelProperty(required = false)
    var order: String = "DESC"
}