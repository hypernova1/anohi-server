package io.hs.anohi.post.ui.payload

import io.hs.anohi.core.Pagination
import io.swagger.annotations.ApiModelProperty

class PostPagination: Pagination() {
    @ApiModelProperty(required = false)
    var emotionId: Long? = null
}