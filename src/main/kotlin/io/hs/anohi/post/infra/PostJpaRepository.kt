package io.hs.anohi.post.infra

import io.hs.anohi.post.domain.Post
import io.hs.anohi.post.domain.PostRepository
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : PostRepository, JpaRepository<Post, Long> {
}