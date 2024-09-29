package io.hs.anohi.post.domain

import io.hs.anohi.core.persistence.AuditEntity
import jakarta.persistence.*

@Table(name = "favorite_post", indexes = [
    Index(name = "favorite_post_account_id_post_id_idx", columnList = "account_id, post_id")
])
@Entity
class FavoritePost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var post: Post,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = false)
    val accountId: Long
) : AuditEntity() {

    companion object {
        fun of(post: Post, accountId: Long): FavoritePost {
            return FavoritePost(post = post, accountId = accountId)
        }
    }
}