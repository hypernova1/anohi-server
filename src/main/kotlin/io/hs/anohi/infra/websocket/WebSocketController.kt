package io.hs.anohi.infra.websocket

import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketController {

    @MessageMapping("/{chatId}/chat")
    @SendTo("/topic/{chatId}/message")
    fun sendMessage(@DestinationVariable chatId: Long, @Payload message: MessageDto): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(message)
    }

    @MessageMapping("/{userId}/notifications")
    @SendTo("/topic/notification/{userId}")
    fun sendNotification(@DestinationVariable userId: Long) {

    }

}