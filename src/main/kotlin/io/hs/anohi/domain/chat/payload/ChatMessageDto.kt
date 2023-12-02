package io.hs.anohi.domain.chat.payload

class ChatMessageDto(
    val id: Long,
    val message: String,
    val createdAt: String,
) {
}