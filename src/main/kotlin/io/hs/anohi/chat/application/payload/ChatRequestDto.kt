package io.hs.anohi.chat.application.payload

import io.swagger.annotations.ApiModelProperty
import jakarta.validation.constraints.NotBlank

data class ChatRequestDto(
    @ApiModelProperty("채팅 요청할 유저의 아이디", example = "1", required = true)
    @field:NotBlank
    val receiverId: Long
)