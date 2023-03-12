package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
class Image: BaseEntity() {

    @Column()
    val originPath: String = ""

    @Column()
    val thumbnailPath: String = ""

    @ManyToOne
    var diary: Diary? = null

}