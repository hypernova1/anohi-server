package io.hs.anohi.chat.infra

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.account.domain.Account
import io.hs.anohi.account.domain.QAccount.account
import io.hs.anohi.chat.domain.ChatRoom
import io.hs.anohi.chat.domain.ChatRoomQueryRepository
import io.hs.anohi.chat.domain.QChatMessage.chatMessage
import io.hs.anohi.chat.domain.QChatRoom.chatRoom
import io.hs.anohi.core.BaseQueryRepository
import org.springframework.stereotype.Repository

@Repository
class ChatRoomQueryJpaRepository : ChatRoomQueryRepository, BaseQueryRepository<ChatRoom>() {

    override fun findByAccount(user: Account): List<ChatRoom> {
        val query = query.selectFrom(chatRoom)
            .leftJoin(chatRoom.accounts, account)
            .leftJoin(chatRoom.messages, chatMessage)

        val whereClause = BooleanBuilder()
        whereClause.and(chatRoom.accounts.contains(user))

        return query.where(whereClause)
            .orderBy(chatMessage.id.desc())
            .orderBy(chatRoom.id.desc())
            .fetchJoin()
            .distinct()
            .fetch()
    }

}