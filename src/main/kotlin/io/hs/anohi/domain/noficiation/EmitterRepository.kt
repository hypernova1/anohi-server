package io.hs.anohi.domain.noficiation

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface EmitterRepository {

    fun save(emitterId: String, sseEmitter: SseEmitter): SseEmitter
    fun saveEventCache(eventCacheId: String, event: Any)
    fun findEmitterStartWithByUserId(userId: Long): Map<String, SseEmitter>
    fun findEventCacheStartWithByUserId(userId: Long): Map<String, Any>
    fun deleteEmitterById(id: String)
    fun deleteEmitterStartWithByUserId(userId: Long)
    fun deleteEventCacheStartWithByUserId(userId: Long)

}