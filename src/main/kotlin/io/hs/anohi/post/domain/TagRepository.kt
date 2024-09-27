package io.hs.anohi.post.domain

interface TagRepository {
    fun findAllByNameIn(name: List<String>): MutableList<Tag>
//    fun saveAll(tags: List<Tag>): List<Tag>
}