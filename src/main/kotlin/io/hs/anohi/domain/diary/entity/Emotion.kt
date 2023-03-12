package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE emotion SET deleted_at = current_timestamp WHERE id = ?")
class Emotion: BaseEntity() {
    @Column
    val name: String = "";

    @ManyToMany(mappedBy = "emotions")
    val diaries: MutableList<Diary> = mutableListOf()
}