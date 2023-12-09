package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.chat.entity.ChatRequest

data class ChatRequestResponseDto(
    val id: Long,
    val sender: Sender,
    val createdAt: String
) {
    constructor(chatRequest: ChatRequest) : this(
        chatRequest.id,
        Sender(chatRequest.sender),
        chatRequest.createdAt.toString()
    )
}