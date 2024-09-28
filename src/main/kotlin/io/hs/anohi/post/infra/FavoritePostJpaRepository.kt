package io.hs.anohi.post.infra

import io.hs.anohi.post.domain.FavoritePost
import io.hs.anohi.post.domain.FavoritePostRepository
import io.hs.anohi.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

interface FavoritePostJpaRepository: FavoritePostRepository, JpaRepository<FavoritePost, Long> {

    @Modifying
    @Transactional
    override fun deleteByPostAndAccountId(post: Post, accountId: Long)

}