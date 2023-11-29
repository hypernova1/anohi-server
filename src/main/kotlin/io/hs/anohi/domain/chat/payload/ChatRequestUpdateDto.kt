package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType
import io.swagger.annotations.ApiModelProperty

data class ChatRequestUpdateDto(
    @ApiModelProperty(allowableValues = "ACCEPT, DENIED")
    val answer: ChatRequestAnswerType
)