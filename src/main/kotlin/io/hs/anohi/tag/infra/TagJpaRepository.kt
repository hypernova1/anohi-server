package io.hs.anohi.tag.infra

import io.hs.anohi.tag.domain.Tag
import io.hs.anohi.tag.domain.TagRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TagJpaRepository: TagRepository, JpaRepository<Tag, Long> {
}