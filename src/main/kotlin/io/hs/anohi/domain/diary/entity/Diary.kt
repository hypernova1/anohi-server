package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.domain.diary.payload.DiaryUpdateForm
import io.hs.anohi.domain.diary.tag.Tag
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.Collections
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE diary SET deleted_at = current_timestamp WHERE id = ?")
class Diary: BaseEntity() {

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = false)
    var content: String = ""

    @ManyToMany(mappedBy = "diaries")
    val categories: MutableList<Category> = mutableListOf()

    @ManyToMany
    val tags: MutableList<Tag> = mutableListOf()

    @ManyToMany
    val emotions: MutableList<Emotion> = mutableListOf()

    @OneToMany
    val images: MutableList<Image> = mutableListOf()

    @ManyToOne
    var account: Account? = null

    fun update(diaryUpdateForm: DiaryUpdateForm) {
        this.title = diaryUpdateForm.title ?: this.title
        this.content = diaryUpdateForm.content ?: this.content
    }

    companion object {

        fun of(diaryRequest: DiaryRequest, account: Account, categories: List<Category>, tags: List<Tag>, emotions: List<Emotion>): Diary {
            val diary = Diary()
            diary.title = diaryRequest.title
            diary.content = diaryRequest.content
            diary.account = account
            diary.categories.addAll(categories)
            diary.tags.addAll(tags)
            diary.emotions.addAll(emotions)
            return diary
        }
    }
}