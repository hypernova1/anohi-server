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
@SQLDelete(sql = "UPDATE category SET deleted_at = current_timestamp WHERE id = ?")
class Category: BaseEntity() {
    @Column
    val name: String = "";

    @ManyToMany(mappedBy = "categories")
    val diaries: MutableList<Diary> = mutableListOf()
}