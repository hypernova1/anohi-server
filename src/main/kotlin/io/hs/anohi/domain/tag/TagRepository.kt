package io.hs.anohi.domain.tag

import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
    fun findAllByNameIn(name: List<String>): MutableList<Tag>
}