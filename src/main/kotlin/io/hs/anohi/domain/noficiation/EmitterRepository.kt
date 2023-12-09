package io.hs.anohi.domain.noficiation

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface EmitterRepository {

    fun save(emitterId: String, sseEmitter: SseEmitter): SseEmitter
    fun saveEventCache(eventCacheId: String, event: Any)

    fun findAllByUserId(userId: Long)

}