package io.hs.anohi.post.domain

interface FavoritePostRepository {

    fun existsByPostAndAccountId(post: Post, accountId: Long): Boolean
    fun deleteByPostAndAccountId(post: Post, accountId: Long)
    fun countByAccountId(accountId: Long): Int
    fun save(favoritePost: FavoritePost): FavoritePost

}