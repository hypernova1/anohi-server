package io.hs.anohi.post.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*

@Entity
class FavoritePost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @ManyToOne
    var post: Post,

    @Column
    val accountId: Long
) : AuditEntity() {


    companion object {
        fun of(post: Post, accountId: Long): FavoritePost {
            return FavoritePost(post = post, accountId = accountId)
        }
    }
}