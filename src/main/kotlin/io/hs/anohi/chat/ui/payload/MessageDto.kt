package io.hs.anohi.chat.ui.payload

import io.hs.anohi.domain.noficiation.constant.NotificationType
import java.time.LocalDateTime

data class MessageDto(
    val content: String,
    val type: NotificationType,
    val url: String? = "",
    val createdAt: String? = LocalDateTime.now().toString()
)