package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account

interface ChatRoomQueryRepository {
    fun findByAccount(user: Account): List<ChatRoom>
}