package io.hs.anohi.domain.noficiation

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class EventEmitterRepositoryImpl: EmitterRepository {

    val emitters = ConcurrentHashMap<String, SseEmitter>()
    val eventCache = ConcurrentHashMap<String, Any>()

    override fun save(emitterId: String, sseEmitter: SseEmitter): SseEmitter {
        emitters[emitterId] = sseEmitter
        return sseEmitter;
    }

    override fun saveEventCache(eventCacheId: String, event: Any) {
        eventCache[eventCacheId] = event
    }

    override fun findEmitterStartWithByUserId(userId: Long): Map<String, SseEmitter> {
        return emitters.entries
            .filter { entry -> entry.key.startsWith(userId.toString()) }
            .associate { it.key to it.value }
    }

    override fun findEventCacheStartWithByUserId(userId: Long): Map<String, Any> {
        return eventCache.entries
            .filter { entry -> entry.key.startsWith(userId.toString()) }
            .associate { it.key to it.value }
    }

    override fun deleteEmitterById(id: String) {
        emitters.remove(id)
    }

    override fun deleteEmitterStartWithByUserId(userId: Long) {
        emitters.forEach { (key, _) ->
            run {
                if (key.startsWith(userId.toString())) {
                    eventCache.remove(key)
                }
            }
        }
    }

    override fun deleteEventCacheStartWithByUserId(userId: Long) {
        eventCache.forEach { (key, _) ->
            run {
                if (key.startsWith(userId.toString())) {
                    eventCache.remove(key)
                }
            }
        }
    }

}