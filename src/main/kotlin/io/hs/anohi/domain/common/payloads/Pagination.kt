package io.hs.anohi.domain.common.payloads

import io.swagger.annotations.ApiModelProperty

open class Pagination {
    @ApiModelProperty(required = false)
    var page: Int = 1

    @ApiModelProperty(required = false)
    var size: Int = 10

    @ApiModelProperty(required = false)
    var lastItemId: Long = 0

    @ApiModelProperty(required = false)
    var order: String = "DESC"
}