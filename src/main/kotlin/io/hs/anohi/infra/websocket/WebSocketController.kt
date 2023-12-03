package io.hs.anohi.infra.websocket

import io.hs.anohi.domain.chat.payload.MessageDto
import org.springframework.context.event.EventListener
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.security.Principal

@Controller
class WebSocketController {

    @MessageMapping("/{chatRoomId}/chat")
    @SendTo("/topic/{chatRoomId}/message")
    fun sendMessage(@DestinationVariable chatRoomId: Long, @Payload message: MessageDto, headerAccessor: SimpMessageHeaderAccessor): ResponseEntity<MessageDto> {
        //TODO: 세션 아이디를 통해 redis에 저장한 후 연결이 끊어지면 rds에 저장
        val sessionId = headerAccessor.sessionId
        return ResponseEntity.ok(message)
    }

    @MessageMapping("/{userId}/notifications")
    @SendTo("/topic/notification/{userId}")
    fun sendNotification(@DestinationVariable userId: Long, headerAccessor: SimpMessageHeaderAccessor) {
        val sessionId = headerAccessor.sessionId
    }

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        println("connect")
        val headerAccessor = SimpMessageHeaderAccessor.wrap(event.message)
        val sessionId = headerAccessor.sessionId
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        println("disconnect")
        val headerAccessor = SimpMessageHeaderAccessor.wrap(event.message)
        val sessionId = headerAccessor.sessionId
    }

}