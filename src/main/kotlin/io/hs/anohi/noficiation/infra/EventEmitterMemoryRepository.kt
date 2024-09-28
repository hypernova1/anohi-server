package io.hs.anohi.noficiation.infra

import io.hs.anohi.noficiation.domain.EmitterRepository
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class EventEmitterMemoryRepository : EmitterRepository {

    private val emitters = ConcurrentHashMap<String, SseEmitter>()
    private val eventCache = ConcurrentHashMap<String, Any>()

    override fun save(userId: Long, sseEmitter: SseEmitter): SseEmitter {
        emitters[userId.toString()] = sseEmitter
        return sseEmitter;
    }

    override fun saveEventCache(eventCacheId: String, event: Any) {
        eventCache[eventCacheId] = event
    }

    override fun findEmitterByUserId(userId: Long): Map<String, SseEmitter> {
        return emitters.entries
            .filter { entry -> entry.key == userId.toString() }
            .associate { it.key to it.value }
    }

    override fun findEventCacheByUserId(userId: Long): Map<String, Any> {
        return eventCache.entries
            .filter { entry -> entry.key == userId.toString() }
            .associate { it.key to it.value }
    }

    override fun deleteEmitterByUserId(userId: Long) {
        emitters.forEach { (key, _) ->
            run {
                if (key == userId.toString()) {
                    eventCache.remove(key)
                }
            }
        }
    }

    override fun deleteEventCacheByUserId(userId: Long) {
        eventCache.forEach { (key, _) ->
            run {
                if (key == userId.toString()) {
                    eventCache.remove(key)
                }
            }
        }
    }

}