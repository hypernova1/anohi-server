package io.hs.anohi.post.domain

import io.hs.anohi.account.domain.Account
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface PostRepository {

    fun findById(id: Long): Optional<Post>
    fun save(post: Post): Post
    fun findAllByAccountAndIdGreaterThan(account: Account, lastItemId: Long, pageable: Pageable): Page<Post>
    fun findAllByEmotionAndAccountAndIdGreaterThan(emotion: Emotion, account: Account, lastItemId: Long, pageable: Pageable): Page<Post>
    fun countByAccount(account: Account): Int
    fun deleteById(id: Long): Unit
}