package io.hs.anohi.domain.chat.payload

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

data class ChatRequestDto(
    @ApiModelProperty("채팅 요청할 유저의 아이디", example = "1", required = true)
    @field:NotBlank
    val receiverId: Long
)