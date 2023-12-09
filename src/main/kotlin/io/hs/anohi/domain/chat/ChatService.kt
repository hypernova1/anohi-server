package io.hs.anohi.domain.chat

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Page
import io.hs.anohi.core.Pagination
import io.hs.anohi.core.exception.BadRequestException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType
import io.hs.anohi.domain.chat.entity.ChatRequest
import io.hs.anohi.domain.chat.payload.*
import io.hs.anohi.domain.chat.repository.ChatRequestQueryRepository
import io.hs.anohi.domain.chat.repository.ChatRequestRepository
import io.hs.anohi.domain.chat.repository.ChatRoomQueryRepository
import io.hs.anohi.domain.noficiation.NotificationEvent
import io.hs.anohi.domain.noficiation.constant.NotificationType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatService(
    private val chatRoomQueryRepository: ChatRoomQueryRepository,
    private val chatRequestRepository: ChatRequestRepository,
    private val chatRequestQueryRepository: ChatRequestQueryRepository,
    private val accountRepository: AccountRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun requestChatting(account: Account, chatRequestDto: ChatRequestDto) {
        val receiver = this.accountRepository.findById(chatRequestDto.receiverId)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        if (account == receiver) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }

        val chatRequest = ChatRequest.of(account, receiver)
        chatRequestRepository.save(chatRequest)
        applicationEventPublisher.publishEvent(
            NotificationEvent(
                this,
                receiver,
                MessageDto(content = "채팅 요청", type = NotificationType.NOTIFICATION)
            )
        )
    }

    @Transactional
    fun updateChatRequest(id: Long, chatRequestUpdateDto: ChatRequestUpdateDto, account: Account) {
        val chatRequest = chatRequestRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_CHAT_REQUEST) }
        if (chatRequest.sender == account) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }
        chatRequest.answer = chatRequestUpdateDto.answer

        //TODO: 채팅 수락시 채팅방 인원에게 알림 전송
        if (chatRequest.answer === ChatRequestAnswerType.ACCEPT) {

        }
    }

    fun findRequests(account: Account, pagination: Pagination): Page<ChatRequestResponseDto> {
        val slice = chatRequestQueryRepository.findByAccount(account, pagination, PageRequest.ofSize(pagination.size))
        val items =
            slice.content.map { ChatRequestResponseDto(it) }
        return Page(pageSize = pagination.size, slice.hasNext(), items)
    }

    fun findRooms(account: Account): List<ChatRoomDto> {
        val chatRooms =
            this.chatRoomQueryRepository.findByAccount(account)

        return chatRooms.map { ChatRoomDto(it, account.id) }
    }
}