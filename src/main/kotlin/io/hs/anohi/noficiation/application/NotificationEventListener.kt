package io.hs.anohi.noficiation.application

import io.hs.anohi.noficiation.domain.NotificationRepository
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Async
@Component
@Transactional(readOnly = true)
class NotificationEventListener(
    private val messagingTemplate: SimpMessagingTemplate,
    private val notificationRepository: NotificationRepository,
    private val sseEmitterService: SseEmitterService,
) {

    @Transactional
    @EventListener
    fun sendNotification(notificationEvent: NotificationEvent) {
        this.sseEmitterService.send(notificationEvent.receiver, notificationEvent.message)
    }

}