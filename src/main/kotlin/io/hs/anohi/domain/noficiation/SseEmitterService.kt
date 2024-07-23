package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

/**
 * TODO: https://gilssang97.tistory.com/69
 * */
@Service
class SseEmitterService(
    private val eventEmitterRepository: EmitterRepository
) {
    private val DEFAULT_TIME_OUT = 60L * 1000 * 60

    @Transactional
    fun subscribe(account: Account, lastEventId: String?): SseEmitter {
        val sseEmitter = this.eventEmitterRepository.save(account.id, SseEmitter(DEFAULT_TIME_OUT))

        sseEmitter.onCompletion { eventEmitterRepository.deleteEmitterByUserId(account.id) }
        sseEmitter.onTimeout { eventEmitterRepository.deleteEmitterByUserId(account.id) }

        // 503 에러 방지용 이벤트
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(account.id.toString())
                    .name("connect")
                    .data("connected.")
            )
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }


        if (lastEventId != null) {
            val events = eventEmitterRepository.findEventCacheByUserId(account.id)
            events.entries
                .filter { entry -> lastEventId < entry.key }
                .forEach { entry -> sendToClient(sseEmitter, entry.key, entry.value) }
        }

        return sseEmitter
    }

    fun send(receiver: Account, messageDto: MessageDto) {
        val sessionEmitters = eventEmitterRepository.findEmitterByUserId(receiver.id)
        sessionEmitters.forEach { (key, emitter) ->
            run {
                this.eventEmitterRepository.saveEventCache(key, Notification())
                sendToClient(emitter, key, messageDto)
            }
        }
    }

    private fun sendToClient(sseEmitter: SseEmitter, id: String, data: Any) {
        try {
            println(sseEmitter)
            println(id)
            sseEmitter.send(
                SseEmitter.event()
                    .id(id)
                    .name("notification")
                    .data(data)
            )
        } catch (_: IOException) {
            eventEmitterRepository.deleteEmitterByUserId(id.toLong())
            throw RuntimeException()
        }
    }

}