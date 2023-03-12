package io.hs.anohi.domain.diary.entity

import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
class Image: BaseEntity() {

    @Column()
    var originPath: String = ""

    @Column()
    var thumbnailPath: String = ""

    @ManyToOne
    var diary: Diary? = null

    companion object {
        fun from(imagePath: String, diary: Diary): Image {
            val image = Image()
            image.originPath = imagePath
            image.diary = diary
            return image
        }
    }

}