package io.hs.anohi.post.domain

interface TagRepository {
    fun findAllByNameIn(name: List<String>): MutableList<Tag>
    fun save(tag: Tag): Tag
}