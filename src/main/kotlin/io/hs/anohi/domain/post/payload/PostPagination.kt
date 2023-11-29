package io.hs.anohi.domain.post.payload

import io.hs.anohi.core.Pagination
import io.swagger.annotations.ApiModelProperty

class PostPagination: Pagination() {
    @ApiModelProperty(required = false)
    var emotionId: Long? = null
}