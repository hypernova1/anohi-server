package io.hs.anohi.chat.application.payload

import io.hs.anohi.chat.domain.ChatRoom
import io.swagger.annotations.ApiModelProperty

data class ChatRoomDto(
    @ApiModelProperty("아이디")
    val id: Long,
    @ApiModelProperty("받는이 정보")
    val receiver: ChatReceiverDto?,
    @ApiModelProperty("최근 메시지 목록")
    val messages: List<MessageDto>,
    @ApiModelProperty("생성일")
    val createdAt: String
) {
    constructor(chatRoom: ChatRoom, accountId: Long) : this(
        id = chatRoom.id,
        receiver = ChatReceiverDto(accountId),
        messages = mutableListOf(),
        createdAt = chatRoom.createdAt.toString()
    ) {
//        val receiver = chatRoom.accounts.first { it.id != accountId }
    }
}