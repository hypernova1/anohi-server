package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.noficiation.NotificationType

data class MessageDto(
    val id: Long,
    val message: String,
    val type: NotificationType,
)