package io.hs.anohi.chat.application

import io.hs.anohi.account.application.AccountService
import io.hs.anohi.account.domain.Account
import io.hs.anohi.chat.domain.*
import io.hs.anohi.chat.ui.payload.*
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
    fun requestChatting(account: Account, chatRequestDto: ChatRequestDto) {
        val receiver = this.accountService.findOne(chatRequestDto.receiverId)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        val existsChatRequest = this.chatRequestRepository.findByReceiverAndSenderAndAnswer(
            receiver,
            account,
            ChatRequestAnswerType.WAITING
        )
        if (existsChatRequest != null && existsChatRequest.answer === ChatRequestAnswerType.WAITING) {
            throw ConflictException(ErrorCode.ALREADY_EXIST_CHAT_REQUEST)
        }

        if (account == receiver) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }

        val chatRequest = ChatRequest.of(account, receiver)
        chatRequestRepository.save(chatRequest)

        applicationEventPublisher.publishEvent(
            NotificationEvent(
                this,
                receiver,
                MessageDto(content = "채팅 요청되었어요.", type = NotificationType.NOTIFICATION)
            )
        )
    }

    @Transactional
    fun answer(id: Long, chatRequestUpdateDto: ChatRequestUpdateDto, account: Account) {
        val chatRequest = chatRequestRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_CHAT_REQUEST) }

        if (chatRequest.isSender(account)) {
            throw BadRequestException(ErrorCode.EQUAL_ACCOUNT)
        }
        chatRequest.answer(chatRequestUpdateDto.answerType)

        if (chatRequest.answer === ChatRequestAnswerType.ACCEPT) {
            this.applicationEventPublisher.publishEvent(
                NotificationEvent(
                    this,
                    chatRequest.receiver,
                    MessageDto("채팅이 수락되었습니다.", NotificationType.ACCEPT_CHAT)
                )
            )
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