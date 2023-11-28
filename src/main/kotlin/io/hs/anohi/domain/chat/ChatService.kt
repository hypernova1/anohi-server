package io.hs.anohi.domain.chat

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.chat.entity.ChatRequest
import io.hs.anohi.domain.chat.payload.ChatRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatService(private val chatRequestRepository: ChatRequestRepository, private val accountRepository: AccountRepository) {

    @Transactional
    fun requestChatting(account: Account, chatRequestDto: ChatRequestDto) {
        val receiver = this.accountRepository.findById(chatRequestDto.receiverId)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }
        val chatRequest = ChatRequest.of(account, receiver)
        chatRequestRepository.save(chatRequest)
    }

    @Transactional
    fun acceptChatting(id: Long, account: Account) {
        val chatRequest = chatRequestRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_CHAT_REQUEST) }
        chatRequest.accept = true
    }
}