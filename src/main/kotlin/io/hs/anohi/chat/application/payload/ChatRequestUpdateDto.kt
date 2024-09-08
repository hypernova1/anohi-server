package io.hs.anohi.chat.ui.payload

import io.hs.anohi.chat.domain.ChatRequestAnswerType
import io.swagger.annotations.ApiModelProperty

data class ChatRequestUpdateDto(
    @ApiModelProperty(allowableValues = "ACCEPT, DENIED")
    val answerType: ChatRequestAnswerType
)