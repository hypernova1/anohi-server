package io.hs.anohi.domain.chat.repository

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.core.BaseQueryRepository
import io.hs.anohi.domain.account.Account
import io.hs.anohi.core.Pagination
import io.hs.anohi.domain.account.QAccount.account
import io.hs.anohi.domain.chat.entity.ChatRoom
import io.hs.anohi.domain.chat.entity.QChatRoom.chatRoom
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class ChatRoomQueryRepository : BaseQueryRepository<ChatRoom>() {

    fun findByAccount(user: Account, pagination: Pagination, pageable: Pageable): SliceImpl<ChatRoom> {
        val query = query.selectFrom(chatRoom)
            .leftJoin(chatRoom.accounts, account)

        val whereClause = BooleanBuilder()
        whereClause.and(chatRoom.accounts.contains(user))

        if (pagination.lastItemId != 0L) {
            whereClause.and(ltId(pagination.lastItemId, pagination.order, chatRoom.id))
        }

        val results = query.where(whereClause).orderBy(chatRoom.id.desc())
            .limit(pageable.pageSize.toLong() + 1)
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }

}