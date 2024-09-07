package io.hs.anohi.chat.ui.payload

import io.hs.anohi.noficiation.domain.NotificationType
import java.time.LocalDateTime

data class MessageDto(
    val content: String,
    val type: NotificationType,
    val url: String? = "",
    val createdAt: String? = LocalDateTime.now().toString()
)