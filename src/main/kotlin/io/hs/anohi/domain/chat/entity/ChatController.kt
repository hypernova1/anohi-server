package io.hs.anohi.domain.chat.entity

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/request")
    fun requestChatting() {

    }

    @PatchMapping("/accept")
    fun acceptChatting() {

    }
}