package io.hs.anohi.noficiation.ui

import io.hs.anohi.infra.security.AuthUser
import io.hs.anohi.noficiation.application.SseEmitterService
import io.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Api(tags = ["알림"])
@RestController
@RequestMapping("/v1/notifications")
class NotificationController(private val sseEmitterService: SseEmitterService) {

    @GetMapping(value = ["/subscribe"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(
        authUser: AuthUser,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") lastEventId: String?
    ): ResponseEntity<SseEmitter> {
        val subscribe = sseEmitterService.subscribe(authUser.id, lastEventId)
        return ResponseEntity.ok(subscribe)
    }

}