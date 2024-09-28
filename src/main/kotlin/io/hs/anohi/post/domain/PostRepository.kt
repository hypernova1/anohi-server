package io.hs.anohi.post.domain

interface PostRepository {

    fun findById(id: Long): Post?
    fun findByIdAndAccountId(id: Long, accountId: Long): Post?
    fun save(post: Post): Post
    fun countByAccountId(accountId: Long): Int
    fun deleteById(id: Long): Unit
}