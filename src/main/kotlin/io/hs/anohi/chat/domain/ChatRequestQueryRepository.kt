package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.Pagination
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl

interface ChatRequestQueryRepository {
    fun findByAccount(user: Account, pagination: Pagination, pageable: Pageable): SliceImpl<ChatRequest>
}