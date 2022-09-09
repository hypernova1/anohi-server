package io.hs.anohi.domain.diary

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.diary.payload.DiaryRequest
import io.hs.anohi.infra.security.AuthAccount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

    @GetMapping
    fun findAll() {

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        diaryService.delete(id)
        ResponseEntity.noContent()
    }

}