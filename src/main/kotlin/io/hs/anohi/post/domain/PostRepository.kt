package io.hs.anohi.post.domain

import java.util.*

interface PostRepository {

    fun findById(id: Long): Optional<Post>
    fun save(post: Post): Post
    fun countByAccountId(accountId: Long): Int
    fun deleteById(id: Long): Unit
}