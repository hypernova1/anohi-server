package io.hs.anohi.domain.diary

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.domain.diary.payload.DiaryUpdateForm
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DiaryService(
    private val diaryRepository: DiaryRepository
) {

    @Transactional
    fun create(diaryRequest: DiaryRequest, account: Account): Diary {
        val diary = Diary.of(diaryRequest, account)
        return diaryRepository.save(diary)
    }

    @Transactional
    fun delete(id: Long) {
        diaryRepository.deleteById(id)
    }

    fun findAll(page: Int, size: Int): List<Diary> {
        return diaryRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")).content
    }

    fun findById(id: Long): Diary {
        val diary = diaryRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_DIARY) }

        return diary
    }

    @Transactional
    fun update(id: Long, diaryUpdateForm: DiaryUpdateForm, account: Account): Diary {
        val diary = diaryRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_DIARY) }
        diary.update(diaryUpdateForm)

        return diary
    }

}