package io.hs.anohi.domain.chat.repository

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.core.BaseQueryRepository
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.entity.ChatRequest
import io.hs.anohi.core.Pagination
import io.hs.anohi.domain.account.QAccount.account
import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType
import io.hs.anohi.domain.chat.entity.QChatRequest.chatRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class ChatRequestQueryRepository: BaseQueryRepository<ChatRequest>() {

    fun findByAccount(user: Account, pagination: Pagination, pageable: Pageable): SliceImpl<ChatRequest> {
        val query = query.selectFrom(chatRequest)
            .leftJoin(chatRequest.receiver, account)

        val whereClause = BooleanBuilder()
        whereClause.and(chatRequest.answer.eq(ChatRequestAnswerType.WAITING))
        whereClause.and(chatRequest.receiver.id.eq(user.id))

        if (pagination.lastItemId != 0L) {
            whereClause.and(ltId(pagination.lastItemId, pagination.order, chatRequest.id))
        }

        val results = query.where(whereClause).orderBy(chatRequest.id.desc())
            .limit(pageable.pageSize.toLong() + 1)
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }

}