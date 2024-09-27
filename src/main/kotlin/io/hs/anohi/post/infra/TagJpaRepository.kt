package io.hs.anohi.post.infra

import io.hs.anohi.post.domain.Tag
import io.hs.anohi.post.domain.TagRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TagJpaRepository: TagRepository, JpaRepository<Tag, Long> {

}