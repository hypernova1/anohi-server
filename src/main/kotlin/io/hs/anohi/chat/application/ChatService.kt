package io.hs.anohi.chat.application

import io.hs.anohi.account.application.AccountService
import io.hs.anohi.chat.application.payload.*
import io.hs.anohi.chat.domain.*
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Page
import io.hs.anohi.core.Pagination
import io.hs.anohi.core.exception.BadRequestException
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.noficiation.application.NotificationEvent
import io.hs.anohi.noficiation.domain.NotificationType
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
    private val accountService: AccountService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun requestChatting(accountId: Long, chatRequestDto: ChatRequestDto) {
        val receiver = this.accountService.findOne(chatRequestDto.receiverId)
            ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT)

        if (accountId == receiver.id) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }

        val existsChatRequest = this.chatRequestRepository.findByReceiverIdAndSenderIdAndAnswerStatus(
            receiverId = receiver.id,
            senderId = accountId,
            ChatRequestAnswerStatus.WAITING
        )
        if (existsChatRequest?.isWaiting() == true) {
            throw ConflictException(ErrorCode.ALREADY_EXIST_CHAT_REQUEST)
        }

        val chatRequest = ChatRequest.of(accountId, receiver.id)
        chatRequestRepository.save(chatRequest)

        applicationEventPublisher.publishEvent(
            NotificationEvent(
                this,
                receiver.id,
                MessageDto(content = "채팅 요청되었어요.", type = NotificationType.NOTIFICATION)
            )
        )
    }

    @Transactional
    fun answer(id: Long, chatRequestUpdateDto: ChatRequestUpdateDto, accountId: Long) {
        val chatRequest =
            chatRequestRepository.findById(id) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_CHAT_REQUEST)

        if (chatRequest.isSender(accountId)) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }
        chatRequest.answer(chatRequestUpdateDto.answerType)

        if (chatRequest.answerStatus === ChatRequestAnswerStatus.ACCEPT) {
            this.applicationEventPublisher.publishEvent(
                NotificationEvent(
                    this,
                    chatRequest.receiverId,
                    MessageDto("채팅이 수락되었습니다.", NotificationType.ACCEPT_CHAT)
                )
            )
        }
    }

    fun findRequests(accountId: Long, pagination: Pagination): Page<ChatRequestResponseDto> {
        val slice =
            chatRequestQueryRepository.findByAccountId(accountId, pagination, PageRequest.ofSize(pagination.size))
        val items = slice.content.map { ChatRequestResponseDto(it) }
        return Page(pageSize = pagination.size, slice.hasNext(), items)
    }

    fun findRooms(accountId: Long): List<ChatRoomDto> {
        val chatRooms =
            this.chatRoomQueryRepository.findByAccountId(accountId)

        return chatRooms.map { ChatRoomDto(it, accountId) }
    }
}