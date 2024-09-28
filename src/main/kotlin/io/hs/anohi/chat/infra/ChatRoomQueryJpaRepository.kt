package io.hs.anohi.chat.infra

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.chat.domain.ChatRoom
import io.hs.anohi.chat.domain.ChatRoomQueryRepository
import io.hs.anohi.chat.domain.QChatMessage.chatMessage
import io.hs.anohi.chat.domain.QChatRoom.chatRoom
import io.hs.anohi.chat.domain.QChatRoomAccount.chatRoomAccount
import io.hs.anohi.core.persistence.BaseQueryRepository
import org.springframework.stereotype.Repository

@Repository
class ChatRoomQueryJpaRepository : ChatRoomQueryRepository, BaseQueryRepository<ChatRoom>() {

    override fun findByAccountId(accountId: Long): List<ChatRoom> {
        val query = query.selectFrom(chatRoom)
            .leftJoin(chatRoom.accounts, chatRoomAccount)

        val whereClause = BooleanBuilder()
        whereClause.and(chatRoomAccount.accountId.eq(accountId))

        return query.where(whereClause)
            .orderBy(chatMessage.id.desc())
            .orderBy(chatRoom.id.desc())
            .fetchJoin()
            .distinct()
            .fetch()
    }

}