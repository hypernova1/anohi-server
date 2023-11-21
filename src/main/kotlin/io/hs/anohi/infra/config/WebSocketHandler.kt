package io.hs.anohi.infra.config

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer


@Component
class WebSocketHandler: TextWebSocketHandler() {

    private val clients = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        clients[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        clients.remove(session.id)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val id = session.id //메시지를 보낸 아이디
        clients.entries.forEach(Consumer<Map.Entry<String, WebSocketSession>> { (key, value): Map.Entry<String, WebSocketSession> ->
            if (key != id) {  //같은 아이디가 아니면 메시지를 전달합니다.
                try {
                    value.sendMessage(message)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        super.handleTransportError(session, exception)
    }

}