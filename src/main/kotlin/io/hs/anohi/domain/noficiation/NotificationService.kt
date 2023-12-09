package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.noficiation.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val eventEmitterRepository: EmitterRepository
) {
    private val DEFAULT_TIMEOUT = 60L * 1000 * 60

    fun subscribe(account: Account, lastEventId: String?): SseEmitter {
        val id = "${account.id}_${System.currentTimeMillis()}"

        val sseEmitter = SseEmitter()

        // 503 에러 방지용 이벤트
        sseEmitter.send(
            SseEmitter.event()
                .name("connect")
                .data("connected.")
        )

        return sseEmitter;
    }

}