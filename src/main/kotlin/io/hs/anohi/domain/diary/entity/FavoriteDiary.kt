package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class FavoriteDiary: BaseEntity() {

    @ManyToOne
    lateinit var diary: Diary

    @ManyToOne
    lateinit var account: Account

    companion object {
        fun of(diary: Diary, account: Account): FavoriteDiary {
            val favoriteDiary = FavoriteDiary()
            favoriteDiary.diary = diary
            favoriteDiary.account = account
            return favoriteDiary
        }
    }
}