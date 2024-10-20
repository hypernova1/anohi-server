package io.hs.anohi.post.application

import io.hs.anohi.post.domain.Tag
import io.hs.anohi.post.domain.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TagService(private val tagRepository: TagRepository) {

    @Transactional
    fun findAllOrCreate(tagNames: List<String>): MutableList<Tag> {
        val tags = tagRepository.findAllByNameIn(tagNames)
        val existTagNames = tags.map { it.name }
        val notExistTagNames = tagNames.filter { !existTagNames.contains(it) }
        val notExistTags = notExistTagNames.map { Tag.create(it) }
        notExistTags.forEach { this.tagRepository.save(it) }

        tags.addAll(notExistTags)

        return tags
    }

}