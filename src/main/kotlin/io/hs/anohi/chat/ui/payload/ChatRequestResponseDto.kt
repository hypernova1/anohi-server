package io.hs.anohi.chat.ui.payload

import io.hs.anohi.chat.domain.ChatRequest


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