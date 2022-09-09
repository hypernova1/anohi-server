package io.hs.anohi.domain.diary

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import org.springframework.stereotype.Service

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository
) {

    fun create(diaryRequest: DiaryRequest, account: Account): Diary {
        val diary = Diary.of(diaryRequest, account)

        return diary
    }

    fun delete(id: Long) {
        diaryRepository.deleteById(id)
    }

}