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

    @Column(name = "origin_url", columnDefinition = "varchar", nullable = false)
    var originUrl: String,

    @Column(name = "thumbnail_url", columnDefinition = "varchar", nullable = true)
    var thumbnailUrl: String = "",

    @Column(name = "width", columnDefinition = "integer", nullable = true)
    var width: Int? = null,

    @Column(name = "height", columnDefinition = "integer", nullable = true)
    var height: Int? = null,

    @Column(name = "blur_hash", columnDefinition = "varchar", nullable = true)
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