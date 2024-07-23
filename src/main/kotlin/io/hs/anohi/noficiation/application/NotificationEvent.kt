package io.hs.anohi.noficiation.application

import io.hs.anohi.account.domain.Account
import io.hs.anohi.chat.ui.payload.MessageDto
import org.springframework.context.ApplicationEvent

class NotificationEvent(source: Any?, val receiver: Account, val message: MessageDto) : ApplicationEvent(source!!)

