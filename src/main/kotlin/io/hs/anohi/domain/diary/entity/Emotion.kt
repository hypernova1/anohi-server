package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.diary.constant.EmotionType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE emotion SET deleted_at = current_timestamp WHERE id = ?")
class Emotion: BaseEntity() {
    @Column
    @Enumerated(EnumType.STRING)
    val name: EmotionType = EmotionType.HAPPY;

    @ManyToMany(mappedBy = "emotions")
    val diaries: MutableList<Diary> = mutableListOf()
}