package io.hs.anohi.domain.noficiation

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.context.ApplicationEvent

class NotificationEvent : ApplicationEvent {
    val receiver: Account
    val message: MessageDto

    constructor(source: Any?, receiver: Account, message: MessageDto) : super(source!!) {
        this.receiver = receiver
        this.message = message
    }
}

