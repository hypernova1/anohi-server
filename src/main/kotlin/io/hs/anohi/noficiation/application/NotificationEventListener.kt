package io.hs.anohi.noficiation.application

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Async
@Component
@Transactional(readOnly = true)
class NotificationEventListener(
    private val sseEmitterService: SseEmitterService,
) {

    @Async
    @Transactional
    @EventListener
    fun sendNotification(notificationEvent: NotificationEvent) {
        this.sseEmitterService.send(notificationEvent.receiver, notificationEvent.message)
    }

}