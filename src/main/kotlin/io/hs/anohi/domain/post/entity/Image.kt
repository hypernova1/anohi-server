package io.hs.anohi.domain.post.entity

import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
class Image: BaseEntity() {

    @Column()
    var originUrl: String = ""

    @Column()
    var thumbnailUrl: String = ""

    @ManyToOne
    var post: Post? = null

    companion object {
        fun from(imagePath: String, post: Post): Image {
            val image = Image()
            image.originUrl = imagePath
            image.post = post
            return image
        }
    }

}