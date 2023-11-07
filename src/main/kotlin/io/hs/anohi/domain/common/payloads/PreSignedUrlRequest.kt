package io.hs.anohi.domain.common.payloads

import io.hs.anohi.domain.common.constans.FolderType
import io.swagger.annotations.ApiModelProperty

class PreSignedUrlRequest {
    @ApiModelProperty("제목", example = "1", required = false)
    val size: Int = 1

    @ApiModelProperty("내용", example = "jpg", required = false)
    val extension: String = "jpg"

    @ApiModelProperty("태그 목록", example = "IMAGE", required = false)
    val folder: FolderType = FolderType.IMAGE
}