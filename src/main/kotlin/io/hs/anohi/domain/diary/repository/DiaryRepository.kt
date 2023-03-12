package io.hs.anohi.domain.diary.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.entity.Diary
import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DiaryRepository : JpaRepository<Diary, Long> {

    fun findAllByAccount(account: Account, pageRequest: AbstractPageRequest): Page<Diary>
}