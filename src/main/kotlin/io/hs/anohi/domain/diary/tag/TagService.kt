package io.hs.anohi.domain.diary.tag

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
        this.tagRepository.saveAll(notExistTags);

        tags.addAll(notExistTags)

        return tags
    }

}