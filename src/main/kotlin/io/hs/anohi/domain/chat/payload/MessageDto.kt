package io.hs.anohi.domain.chat.payload

import io.hs.anohi.domain.noficiation.constant.NotificationType

data class MessageDto(
    var id: Long = 0,
    val content: String,
    val type: NotificationType,
    val url: String?
)