package io.hs.anohi.chat.ui

import io.hs.anohi.chat.application.ChatService
import io.hs.anohi.chat.application.payload.ChatRequestDto
import io.hs.anohi.chat.application.payload.ChatRequestResponseDto
import io.hs.anohi.chat.application.payload.ChatRequestUpdateDto
import io.hs.anohi.chat.application.payload.ChatRoomDto
import io.hs.anohi.core.Page
import io.hs.anohi.core.Pagination
import io.hs.anohi.infra.annotations.QueryStringArgumentResolver
import io.hs.anohi.infra.security.AuthUser
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
        authUser: AuthUser,
        @RequestBody chatRequestDto: ChatRequestDto
    ): ResponseEntity<Any> {
        this.chatService.requestChatting(authUser.id, chatRequestDto)
        return ResponseEntity.ok().build()
    }

    @ApiOperation("요청 받은 채팅 응답")
    @PatchMapping("/{id}")
    fun acceptChatting(@PathVariable id: Long, @RequestBody chatRequestUpdateDto: ChatRequestUpdateDto, authUser: AuthUser): ResponseEntity<Any> {
        this.chatService.answer(id, chatRequestUpdateDto, authUser.id)
        return ResponseEntity.ok().build()
    }

    @ApiOperation("요청 받은 채팅 목록 (승인 거절 제외 대기중인 목록)")
    @GetMapping("/requests")
    fun getChatRequestList(authUser: AuthUser, @QueryStringArgumentResolver pagination: Pagination): ResponseEntity<Page<ChatRequestResponseDto>> {
        val result = this.chatService.findRequests(authUser.id, pagination)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("채팅 방 목록")
    @GetMapping
    fun getChatRooms(authUser: AuthUser): ResponseEntity<List<ChatRoomDto>> {
        val result = this.chatService.findRooms(authUser.id)
        return ResponseEntity.ok(result)
    }

}