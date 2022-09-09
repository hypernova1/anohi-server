package io.hs.anohi.domain.diary

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE diary SET deleted_at = current_timestamp WHERE id = ?")
class Diary: BaseEntity() {

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = true)
    var content: String = ""

    @ManyToOne
    var account: Account? = null

    companion object {
        fun of(diaryRequest: DiaryRequest, account: Account): Diary {
            val diary = Diary()
            diary.title = diaryRequest.title
            diary.content = diaryRequest.content
            diary.account = account
            return diary
        }
    }
}