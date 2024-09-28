package io.hs.anohi.chat.domain

interface ChatRoomQueryRepository {
    fun findByAccountId(accountId: Long): List<ChatRoom>
}