package io.hs.anohi.domain

import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["앱 관련"])
@RestController
class AppController {

    @GetMapping("/health")
    fun checkHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

}