package io.hs.anohi.domain.post.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.ImageDto
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
class Image: BaseEntity() {

    @Column(columnDefinition = "text")
    var originUrl: String = ""

    @Column(columnDefinition = "text")
    var thumbnailUrl: String = ""

    @Column(columnDefinition = "integer")
    var width: Int = 0

    @Column(columnDefinition = "integer")
    var height: Int = 0

    @Column(columnDefinition = "varchar(255)")
    var blurHash: String = ""

    @ManyToMany(mappedBy = "images")
    var posts: MutableList<Post> = mutableListOf()

    @ManyToMany(mappedBy = "images")
    var users: MutableList<Account> = mutableListOf()

    companion object {
        fun from(dto: ImageDto): Image {
            val image = Image()
            image.originUrl = dto.path
            image.width = dto.width ?: 0
            image.height = dto.height ?: 0
            image.blurHash = dto.blurHash ?: ""
            return image
        }
    }

}