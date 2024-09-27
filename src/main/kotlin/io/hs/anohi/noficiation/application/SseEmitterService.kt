package io.hs.anohi.noficiation.application

import io.hs.anohi.account.domain.Account
import io.hs.anohi.chat.application.payload.MessageDto
import io.hs.anohi.noficiation.domain.EmitterRepository
import io.hs.anohi.noficiation.domain.Notification
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
    private val TIME_OUT = 60L * 1000 * 60

    @Transactional
    fun subscribe(account: Account, lastEventId: String?): SseEmitter {
        val sseEmitter = createAndSaveEmitter(account)

        handleReconnectEvents(sseEmitter, account.id, lastEventId)
        return sseEmitter
    }

    fun send(receiver: Account, messageDto: MessageDto) {
        val sessionEmitters = eventEmitterRepository.findEmitterByUserId(receiver.id)
        sessionEmitters.forEach { (key, emitter) ->
            eventEmitterRepository.saveEventCache(key, Notification())
            sendToClient(emitter, key, messageDto)
        }
    }

    private fun createAndSaveEmitter(account: Account): SseEmitter {
        val sseEmitter = eventEmitterRepository.save(account.id, SseEmitter(TIME_OUT))

        sseEmitter.onCompletion { eventEmitterRepository.deleteEmitterByUserId(account.id) }
        sseEmitter.onTimeout { eventEmitterRepository.deleteEmitterByUserId(account.id) }

        sendInitialConnectEvent(sseEmitter, account.id)
        return sseEmitter
    }

    /**
     * 503 에러 방지용 기본 이벤트 전송
    */
    private fun sendInitialConnectEvent(sseEmitter: SseEmitter, userId: Long) {
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(userId.toString())
                    .name("connect")
                    .data("connected.")
            )
        } catch (e: IOException) {
            eventEmitterRepository.deleteEmitterByUserId(userId)
            throw RuntimeException(e)
        }
    }

    private fun handleReconnectEvents(sseEmitter: SseEmitter, userId: Long, lastEventId: String?) {
        if (lastEventId != null) {
            val events = eventEmitterRepository.findEventCacheByUserId(userId)
            events.entries
                .filter { entry -> lastEventId < entry.key }
                .forEach { entry -> sendToClient(sseEmitter, entry.key, entry.value) }
        }
    }

    private fun sendToClient(sseEmitter: SseEmitter, id: String, data: Any) {
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(id)
                    .name("notification")
                    .data(data)
            )
        } catch (e: IOException) {
            eventEmitterRepository.deleteEmitterByUserId(id.toLong())
            throw RuntimeException(e)
        }
    }
}