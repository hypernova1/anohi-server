package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.domain.diary.payload.DiaryUpdateForm
import io.hs.anohi.domain.tag.Tag
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.Collections
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE diary SET deleted_at = current_timestamp WHERE id = ?")
class Diary: BaseEntity() {

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = false)
    var content: String = ""

    @Column(nullable = false)
    var hit: Long = 0

    @Column(nullable = false)
    var favoriteCount: Long = 0

    @ManyToMany
    var categories: MutableList<Category> = mutableListOf()

    @ManyToMany
    var tags: MutableList<Tag> = mutableListOf()

    @ManyToMany
    var emotions: MutableList<Emotion> = mutableListOf()

    @OneToMany
    var images: MutableList<Image> = mutableListOf()

    @ManyToOne
    lateinit var account: Account

    @OneToMany
    val favoriteDiaries: MutableList<FavoriteDiary> = mutableListOf()

    fun update(diaryUpdateForm: DiaryUpdateForm, categories: List<Category>?, tags: List<Tag>?, emotions: List<Emotion>?) {
        this.title = diaryUpdateForm.title ?: this.title
        this.content = diaryUpdateForm.content ?: this.content
        this.categories = (categories ?: mutableListOf()).toMutableList()
        this.tags = (tags ?: mutableListOf()).toMutableList()
        this.emotions = (emotions ?: mutableListOf()).toMutableList()
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