package io.hs.anohi.image

import io.hs.anohi.account.domain.AccountImage
import io.hs.anohi.core.persistence.AuditEntity
import io.hs.anohi.post.application.payload.ImageDto
import io.hs.anohi.post.domain.PostImage
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
    val id: Long = 0,

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

    @OneToMany(mappedBy = "image", cascade = [CascadeType.ALL])
    val accountImages: List<AccountImage> = mutableListOf(),

    @OneToMany(mappedBy = "image", cascade = [CascadeType.ALL])
    val postImages: List<PostImage> = mutableListOf()
) : AuditEntity() {

    companion object {
        fun from(dto: ImageDto): Image {
            return Image(originUrl = dto.path, width = dto.width, height = dto.height, blurHash = dto.blurHash)
        }
    }

}