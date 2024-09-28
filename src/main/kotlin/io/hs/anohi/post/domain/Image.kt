package io.hs.anohi.post.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.AuditEntity
import io.hs.anohi.post.application.payload.ImageDto
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Column(columnDefinition = "text")
    var originUrl: String,

    @Column(columnDefinition = "text")
    var thumbnailUrl: String = "",

    @Column(columnDefinition = "integer")
    var width: Int? = null,

    @Column(columnDefinition = "integer")
    var height: Int? = null,

    @Column(columnDefinition = "varchar(255)")
    var blurHash: String? = null,

    @ManyToMany(mappedBy = "images")
    var posts: MutableList<Post> = mutableListOf(),

    @ManyToMany(mappedBy = "images")
    var users: MutableList<Account> = mutableListOf(),

    @ManyToMany(mappedBy = "backgroundImages")
    var users2: MutableList<Account> = mutableListOf()
) : AuditEntity() {

    companion object {
        fun from(dto: ImageDto): Image {
            return Image(originUrl = dto.path, width = dto.width, height = dto.height, blurHash = dto.blurHash)
        }
    }

}