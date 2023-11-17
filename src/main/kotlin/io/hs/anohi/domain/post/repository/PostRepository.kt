package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.entity.Emotion
import io.hs.anohi.domain.post.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByAccountAndIdGreaterThan(account: Account, lastItemId: Long, pageable: Pageable): Page<Post>
    fun findAllByEmotionAndAccountAndIdGreaterThan(emotion: Emotion, account: Account, lastItemId: Long, pageable: Pageable): Page<Post>
    fun countByAccount(account: Account): Int
}