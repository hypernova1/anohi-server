package io.hs.anohi.post.domain

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class FavoritePost: BaseEntity() {

    @ManyToOne
    lateinit var post: Post

    @ManyToOne
    lateinit var account: Account

    companion object {
        fun of(post: Post, account: Account): FavoritePost {
            val favoritePost = FavoritePost()
            favoritePost.post = post
            favoritePost.account = account
            return favoritePost
        }
    }
}