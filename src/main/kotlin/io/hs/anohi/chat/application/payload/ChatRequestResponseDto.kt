package io.hs.anohi.chat.application.payload

import io.hs.anohi.chat.domain.ChatRequest


data class ChatRequestResponseDto(
    val id: Long,
    val senderId: Long,
    val createdAt: String
) {
    constructor(chatRequest: ChatRequest) : this(
        chatRequest.id,
        chatRequest.senderId,
        chatRequest.createdAt.toString()
    )
}