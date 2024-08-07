package io.hs.anohi.noficiation.application

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.context.ApplicationEvent

class NotificationEvent(source: Any?, val receiver: Account, val message: MessageDto) : ApplicationEvent(source!!)

