package io.hs.anohi.domain.diary.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.entity.Diary
import io.hs.anohi.domain.diary.entity.FavoriteDiary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

interface FavoriteDiaryRepository: JpaRepository<FavoriteDiary, Long> {

    fun existsByDiaryAndAccount(diary: Diary, account: Account): Boolean

    @Modifying
    @Transactional
    fun deleteByDiaryAndAccount(diary: Diary, account: Account)


}