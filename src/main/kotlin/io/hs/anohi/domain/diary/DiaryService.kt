package io.hs.anohi.domain.diary

import org.springframework.stereotype.Service

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository
) {

    fun delete(id: Long) {
        diaryRepository.deleteById(id)
    }

}