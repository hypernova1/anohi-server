package io.hs.anohi.domain.diary

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryDetail
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.domain.diary.payload.DiarySummary
import io.hs.anohi.domain.diary.payload.DiaryUpdateForm
import io.hs.anohi.infra.security.AuthAccount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/v1/diaries")
class DiaryController(
    private val diaryService: DiaryService
) {

    @PostMapping
    fun create(@Valid @RequestBody diaryRequest: DiaryRequest, @AuthAccount account: Account): ResponseEntity<Any> {
        val diary = diaryService.create(diaryRequest, account)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(diary.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody diaryUpdateForm: DiaryUpdateForm, @AuthAccount account: Account) {
        diaryService.update(id, diaryUpdateForm, account)
    }

    @GetMapping
    fun findAll(@RequestParam(defaultValue = "1") page: Int,
                @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<DiarySummary>> {
        val diaries = diaryService.findAll(page, size)
        val diarySummaries = diaries.map { DiarySummary(it.title, it.content) }

        return ResponseEntity.ok(diarySummaries)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): ResponseEntity<DiaryDetail> {
        val diary = diaryService.findById(id)
        val diaryDetail = DiaryDetail(diary.title, diary.content)

        return ResponseEntity.ok(diaryDetail)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        diaryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}