package io.hs.anohi.domain.diary

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/diaries")
class DiaryController(
    private val diaryService: DiaryService
) {

    @GetMapping
    fun findAll() {

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        diaryService.delete(id)
        ResponseEntity.noContent()
    }

}