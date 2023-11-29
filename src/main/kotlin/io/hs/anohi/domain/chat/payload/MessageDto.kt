package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.noficiation.NotificationType

data class MessageDto(
    val content: String,
    val type: NotificationType,
    var id: Long = 0,
)