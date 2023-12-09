package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.infra.security.AuthAccount
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@RestController
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @GetMapping(value = ["/subscribe"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(
        @AuthAccount account: Account,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") lastEventId: String?
    ): SseEmitter {
        return notificationService.subscribe(account, lastEventId)
    }

}