package io.hs.anohi.infra.websocket

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.chat.ui.payload.MessageDto
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.UnauthorizedException
import org.springframework.context.event.EventListener
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent


@Controller
class WebSocketController(
    private var firebaseAuth: FirebaseAuth
) : TextWebSocketHandler() {

    @MessageMapping("/{chatRoomId}/chat")
    @SendTo("/topic/{chatRoomId}/message")
    fun sendMessage(
        @DestinationVariable chatRoomId: Long,
        @Payload message: MessageDto,
        headerAccessor: SimpMessageHeaderAccessor
    ): ResponseEntity<MessageDto> {
        //TODO: 세션 아이디를 통해 redis에 저장한 후 연결이 끊어지면 rds에 저장
        val uid = extractUidFromToken(headerAccessor)
        return ResponseEntity.ok(message)
    }

    @MessageMapping("/{userId}/notifications")
    @SendTo("/topic/notification/{userId}")
    fun sendNotification(@DestinationVariable userId: Long, headerAccessor: SimpMessageHeaderAccessor) {
        val uid = extractUidFromToken(headerAccessor)

    }

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        println("connect")
        val headerAccessor = SimpMessageHeaderAccessor.wrap(event.message)
        extractUidFromToken(headerAccessor)
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        println("disconnect")
        val headerAccessor = SimpMessageHeaderAccessor.wrap(event.message)
        extractUidFromToken(headerAccessor)
    }

    fun extractUidFromToken(headerAccessor: SimpMessageHeaderAccessor): String {
        val messageHeaders = headerAccessor.messageHeaders
        val headers = messageHeaders["nativeHeaders"] as Map<*, *>

        val message = headers["Authorization"] as List<*>
        val token = message[0] as String? ?: throw UnauthorizedException(ErrorCode.INVALID_TOKEN)

        val verifyIdToken = firebaseAuth.verifyIdToken(token.split(" ")[1])
        return verifyIdToken.uid
    }

}