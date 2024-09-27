package io.hs.anohi.chat.infra

import io.hs.anohi.account.domain.Account
import io.hs.anohi.chat.domain.ChatRequest
import io.hs.anohi.chat.domain.ChatRequestAnswerStatus
import io.hs.anohi.chat.domain.ChatRequestRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRequestJpaRepository: ChatRequestRepository, JpaRepository<ChatRequest, Long> {

    override fun findByReceiverAndSenderAndAnswer(receiver: Account, sender: Account, answer: ChatRequestAnswerStatus): ChatRequest?

}