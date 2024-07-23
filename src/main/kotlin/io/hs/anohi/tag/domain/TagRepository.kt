package io.hs.anohi.tag.domain

interface TagRepository {
    fun findAllByNameIn(name: List<String>): MutableList<Tag>
    fun saveAll(tags: List<Tag>): List<Tag>
}