package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.chat.application.payload.ChatRequestDto
import java.util.Optional

interface ChatRequestRepository {

    fun save(chatRequest: ChatRequest): ChatRequestDto
    fun findById(id: Long): Optional<ChatRequest>
    fun findByReceiverAndSenderAndAnswer(receiver: Account, sender: Account, answer: ChatRequestAnswerStatus): ChatRequest?
}