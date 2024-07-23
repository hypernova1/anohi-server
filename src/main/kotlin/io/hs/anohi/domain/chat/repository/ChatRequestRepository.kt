package io.hs.anohi.domain.chat.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType
import io.hs.anohi.domain.chat.entity.ChatRequest
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRequestRepository: JpaRepository<ChatRequest, Long> {

    fun findByReceiverAndSenderAndAnswer(receiver: Account, sender: Account, answer: ChatRequestAnswerType): ChatRequest?

}