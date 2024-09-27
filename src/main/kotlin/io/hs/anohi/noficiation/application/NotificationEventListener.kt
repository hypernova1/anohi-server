package io.hs.anohi.noficiation.application

import io.hs.anohi.util.SlackMessageUtil
import org.springframework.context.event.EventListener
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
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
    @Retryable(maxAttempts = 3)
    @EventListener
    fun sendNotification(notificationEvent: NotificationEvent) {
        this.sseEmitterService.send(notificationEvent.receiver, notificationEvent.message)
    }

    @Recover
    fun sendError() {
        SlackMessageUtil.sendMessage()
    }

}