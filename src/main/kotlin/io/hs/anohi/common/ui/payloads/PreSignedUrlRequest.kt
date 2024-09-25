package io.hs.anohi.common.ui.payloads

import io.hs.anohi.common.constans.FolderType
import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

class PreSignedUrlRequest {
    @ApiModelProperty("제목", example = "1", required = false)
    @Min(1)
    @Max(10)
    val size: Int = 1

    @ApiModelProperty("내용", example = "jpg", required = false)
    val extension: String = "jpg"

    @ApiModelProperty("태그 목록", example = "IMAGE", required = false)
    val folder: FolderType = FolderType.IMAGE
}