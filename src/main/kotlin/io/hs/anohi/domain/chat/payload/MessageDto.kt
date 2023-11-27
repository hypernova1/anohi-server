package io.hs.anohi.domain.chat.payload

data class MessageDto(
    val id: Long,
    val message: String,
    val type: String,
) {

}