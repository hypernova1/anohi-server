package io.hs.anohi.chat.domain

import io.hs.anohi.core.Pagination
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl

interface ChatRequestQueryRepository {
    fun findByAccountId(accountId: Long, pagination: Pagination, pageable: Pageable): SliceImpl<ChatRequest>
}