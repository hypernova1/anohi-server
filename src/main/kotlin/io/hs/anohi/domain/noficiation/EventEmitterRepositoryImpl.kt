package io.hs.anohi.domain.noficiation

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Repository
class EventEmitterRepositoryImpl: EmitterRepository {
    override fun save(emitterId: String, sseEmitter: SseEmitter): SseEmitter {
        TODO("Not yet implemented")
    }

    override fun saveEventCache(eventCacheId: String, event: Any) {
        TODO("Not yet implemented")
    }

    override fun findAllByUserId(userId: Long) {
        TODO("Not yet implemented")
    }
}