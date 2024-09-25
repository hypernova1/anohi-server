package io.hs.anohi.chat.application.payload

class ChatMessageDto(
    val id: Long,
    val message: String,
    val createdAt: String,
) {
}