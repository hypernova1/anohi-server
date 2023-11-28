package io.hs.anohi.domain.chat

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.ChatRequestDto
import io.hs.anohi.infra.security.AuthAccount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/request")
    fun requestChatting(@AuthAccount account: Account, @RequestBody chatRequestDto: ChatRequestDto): ResponseEntity<Any> {
        this.chatService.requestChatting(account, chatRequestDto)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{id}/accept")
    fun acceptChatting(@PathVariable id: Long, @AuthAccount account: Account): ResponseEntity<Any> {
        this.chatService.acceptChatting(id, account);
        return ResponseEntity.ok().build()
    }
}