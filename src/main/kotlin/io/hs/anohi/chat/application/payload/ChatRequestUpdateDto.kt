package io.hs.anohi.chat.application.payload

import io.hs.anohi.chat.domain.ChatRequestAnswerStatus
import io.swagger.annotations.ApiModelProperty

data class ChatRequestUpdateDto(
    @ApiModelProperty(allowableValues = "ACCEPT, DENIED")
    val answerType: ChatRequestAnswerStatus
)