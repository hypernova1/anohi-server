package io.hs.anohi.post.application.payload

import io.hs.anohi.core.Pagination
import io.swagger.annotations.ApiModelProperty

class PostPagination: Pagination() {
    @field:ApiModelProperty(required = false)
    val emotionId: Long? = null
}