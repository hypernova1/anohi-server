package io.hs.anohi.domain.chat.repository

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.core.BaseQueryRepository
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.account.QAccount.account
import io.hs.anohi.domain.chat.entity.ChatRoom
import io.hs.anohi.domain.chat.entity.QChatMessage.chatMessage
import io.hs.anohi.domain.chat.entity.QChatRoom.chatRoom
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class ChatRoomQueryRepository : BaseQueryRepository<ChatRoom>() {

    fun findByAccount(user: Account): List<ChatRoom> {
        val query = query.selectFrom(chatRoom)
            .leftJoin(chatRoom.accounts, account)
            .leftJoin(chatRoom.messages, chatMessage)

        val whereClause = BooleanBuilder()
        whereClause.and(chatRoom.accounts.contains(user))

        return query.where(whereClause)
            .orderBy(chatMessage.id.desc())
            .orderBy(chatRoom.id.desc())
            .fetchJoin()
            .fetch()
    }

}