package io.hs.anohi.domain.noficiation

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Async
@Component
@Transactional(readOnly = true)
class NotificationEventListener (
    private val messagingTemplate: SimpMessagingTemplate,
    private val notificationRepository: NotificationRepository
) {

    @Transactional
    @EventListener
    fun sendNotification(notificationEvent: NotificationEvent) {
        messagingTemplate.convertAndSend("/topic/${notificationEvent.receiver.id}/message", notificationEvent.message)
        val notification = Notification.from(notificationEvent)
        this.notificationRepository.save(notification)
    }

}