package io.hs.anohi.chat.domain

import io.hs.anohi.chat.application.payload.ChatRequestDto
import java.util.*

interface ChatRequestRepository {

    fun save(chatRequest: ChatRequest): ChatRequestDto
    fun findById(id: Long): Optional<ChatRequest>
    fun findByReceiverIdAndSenderIdAndAnswerStatus(
        receiverId: Long,
        senderId: Long,
        answer: ChatRequestAnswerStatus
    ): ChatRequest?
}