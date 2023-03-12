package io.hs.anohi.domain.diary

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Pagination
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.entity.Diary
import io.hs.anohi.domain.diary.payload.DiaryDetail
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.domain.diary.payload.DiarySummary
import io.hs.anohi.domain.diary.payload.DiaryUpdateForm
import io.hs.anohi.domain.diary.repository.CategoryRepository
import io.hs.anohi.domain.diary.repository.DiaryRepository
import io.hs.anohi.domain.diary.repository.EmotionRepository
import io.hs.anohi.domain.tag.TagService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DiaryService(
    private val diaryRepository: DiaryRepository,
    private val tagService: TagService,
    private val categoryRepository: CategoryRepository,
    private val emotionRepository: EmotionRepository
) {

    @Transactional
    fun create(diaryRequest: DiaryRequest, account: Account): Diary {
        val categories = categoryRepository.findAllById(diaryRequest.categoryIds)
        println(categories)
        val emotions = emotionRepository.findAllById(diaryRequest.emotionIds)
        val tags = tagService.findAllOrCreate(diaryRequest.tags)
        val diary = Diary.of(diaryRequest = diaryRequest, account = account, categories = categories, emotions = emotions, tags = tags)
        return diaryRepository.save(diary)
    }

    @Transactional
    fun delete(id: Long) {
        diaryRepository.deleteById(id)
    }

    fun findAll(account: Account, page: Int, size: Int): Pagination<DiaryDetail> {
        val pageDiaries =
            diaryRepository.findAllByAccount(account, PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"))
        val diarySummaries = pageDiaries.content.map { DiaryDetail(it) }
        return Pagination.load(pageDiaries, diarySummaries);
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