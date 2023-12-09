package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import io.hs.anohi.domain.noficiation.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.lang.RuntimeException

/**
 * TODO: https://gilssang97.tistory.com/69
 * */
@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val eventEmitterRepository: EmitterRepository
) {
    private val DEFAULT_TIME_OUT = 60L * 1000 * 60

    @Transactional
    fun subscribe(account: Account, lastEventId: String?): SseEmitter {
        val id = "${account.id}_${System.currentTimeMillis()}"

        val sseEmitter = this.eventEmitterRepository.save(id, SseEmitter(DEFAULT_TIME_OUT))

        sseEmitter.onCompletion { eventEmitterRepository.deleteEmitterById(id) }
        sseEmitter.onTimeout { eventEmitterRepository.deleteEmitterById(id) }

        // 503 에러 방지용 이벤트
        sseEmitter.send(
            SseEmitter.event()
                .name("connect")
                .data("connected.")
        )

        if (lastEventId != null) {
            val events = eventEmitterRepository.findEventCacheStartWithByUserId(account.id)
            events.entries
                .filter { entry -> lastEventId < entry.key }
                .forEach { entry -> sendToClient(sseEmitter, entry.key, entry.value) }
        }

        return sseEmitter
    }

    fun send(receiver: Account, messageDto: MessageDto) {
        val sessionEmitters = eventEmitterRepository.findEmitterStartWithByUserId(receiver.id)
        sessionEmitters.forEach { (key, emitter) ->
            run {
                this.eventEmitterRepository.saveEventCache(key, Notification())
                sendToClient(emitter, key, messageDto)
            }
        }
    }

    fun sendToClient(sseEmitter: SseEmitter, id: String, data: Any) {
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data)
            )
        } catch (_: IOException) {
            eventEmitterRepository.deleteEmitterById(id)
            throw RuntimeException()
        }
    }

}