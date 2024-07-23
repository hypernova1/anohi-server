package io.hs.anohi.domain.chat

import io.hs.anohi.core.Page
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.ChatRequestDto
import io.hs.anohi.core.Pagination
import io.hs.anohi.domain.chat.payload.ChatRequestResponseDto
import io.hs.anohi.domain.chat.payload.ChatRequestUpdateDto
import io.hs.anohi.domain.chat.payload.ChatRoomDto
import io.hs.anohi.infra.annotations.QueryStringArgumentResolver
import io.hs.anohi.infra.security.AuthAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(tags = ["채팅 관련"])
@RestController
@RequestMapping("/v1/chat")
class ChatController(private val chatService: ChatService) {

    @ApiOperation("채팅 요청")
    @PostMapping("/requests")
    fun requestChatting(
        @AuthAccount account: Account,
        @RequestBody chatRequestDto: ChatRequestDto
    ): ResponseEntity<Any> {
        this.chatService.requestChatting(account, chatRequestDto)
        return ResponseEntity.ok().build()
    }

    @ApiOperation("요청 받은 채팅 응답")
    @PatchMapping("/{id}")
    fun acceptChatting(@PathVariable id: Long, @RequestBody chatRequestUpdateDto: ChatRequestUpdateDto, @AuthAccount account: Account): ResponseEntity<Any> {
        this.chatService.updateChatRequest(id, chatRequestUpdateDto, account);
        return ResponseEntity.ok().build()
    }

    @ApiOperation("요청 받은 채팅 목록 (승인 거절 제외 대기중인 목록)")
    @GetMapping("/requests")
    fun getChatRequestList(@AuthAccount account: Account, @QueryStringArgumentResolver pagination: Pagination): ResponseEntity<Page<ChatRequestResponseDto>> {
        val result = this.chatService.findRequests(account, pagination)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("채팅 방 목록")
    @GetMapping
    fun getChatRooms(@AuthAccount account: Account): ResponseEntity<List<ChatRoomDto>> {
        val result = this.chatService.findRooms(account)
        return ResponseEntity.ok(result)
    }

}