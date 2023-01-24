package io.hs.anohi.domain

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {

    @GetMapping("/health")
    fun checkHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

}