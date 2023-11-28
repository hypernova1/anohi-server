package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val notificationRepository: NotificationRepository
) {

    @Transactional
    fun sendNotification(account: Account, messageDto: MessageDto) {
        messagingTemplate.convertAndSend("/topic/${account.id}/message", messageDto)
        val notification = Notification.of(messageDto, account)
        this.notificationRepository.save(notification)
    }

}