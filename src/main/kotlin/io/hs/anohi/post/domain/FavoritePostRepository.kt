package io.hs.anohi.post.domain

import io.hs.anohi.account.domain.Account

interface FavoritePostRepository {

    fun existsByPostAndAccount(post: Post, account: Account): Boolean
    fun deleteByPostAndAccount(post: Post, account: Account)
    fun countByAccount(account: Account): Int
    fun save(favoritePost: FavoritePost): FavoritePost

}