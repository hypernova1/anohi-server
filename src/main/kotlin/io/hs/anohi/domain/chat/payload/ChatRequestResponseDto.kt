package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType

data class ChatRequestResponseDto(
    val id: Long,
    val senderId: Long,
    val answer: ChatRequestAnswerType,
    val createdAt: String
    )