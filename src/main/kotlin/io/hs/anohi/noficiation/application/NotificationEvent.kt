package io.hs.anohi.noficiation.application

import io.hs.anohi.chat.application.payload.MessageDto

class NotificationEvent(val source: Any?, val receiverId: Long, val message: MessageDto)

