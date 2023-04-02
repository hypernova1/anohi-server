package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByAccount(account: Account, pageable: Pageable): Page<Post>
    fun countByAccount(account: Account): Int
}