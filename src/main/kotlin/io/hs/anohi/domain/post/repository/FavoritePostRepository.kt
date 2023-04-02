package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.domain.post.entity.FavoritePost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

interface FavoritePostRepository: JpaRepository<FavoritePost, Long> {

    fun existsByPostAndAccount(post: Post, account: Account): Boolean

    @Modifying
    @Transactional
    fun deleteByPostAndAccount(post: Post, account: Account)

    fun countByAccount(account: Account): Int


}