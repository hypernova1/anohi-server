package io.hs.anohi.chat.infra

import com.querydsl.core.BooleanBuilder
import io.hs.anohi.account.domain.QAccount.account
import io.hs.anohi.chat.domain.ChatRequest
import io.hs.anohi.chat.domain.ChatRequestAnswerStatus
import io.hs.anohi.chat.domain.ChatRequestQueryRepository
import io.hs.anohi.core.BaseQueryRepository
import io.hs.anohi.core.Pagination
import io.hs.anohi.chat.domain.QChatRequest.chatRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class ChatRequestQueryJpaRepository: ChatRequestQueryRepository, BaseQueryRepository<ChatRequest>() {

    override fun findByAccountId(accountId: Long, pagination: Pagination, pageable: Pageable): SliceImpl<ChatRequest> {
        val query = query.selectFrom(chatRequest)
            .leftJoin(account).on(chatRequest.receiverId.eq(accountId))

        val whereClause = BooleanBuilder()
        whereClause.and(chatRequest.answerStatus.eq(ChatRequestAnswerStatus.WAITING))
        whereClause.and(chatRequest.receiverId.eq(accountId))

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