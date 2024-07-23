package io.hs.anohi.domain.noficiation

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface EmitterRepository {

    fun save(userId: Long, sseEmitter: SseEmitter): SseEmitter
    fun saveEventCache(eventCacheId: String, event: Any)
    fun findEmitterByUserId(userId: Long): Map<String, SseEmitter>
    fun findEventCacheByUserId(userId: Long): Map<String, Any>
    fun deleteEmitterByUserId(userId: Long)
    fun deleteEventCacheByUserId(userId: Long)

}