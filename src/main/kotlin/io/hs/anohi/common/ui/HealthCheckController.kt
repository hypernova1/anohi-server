package io.hs.anohi.common.ui

import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["도메인에 관련 없는 API 모음"])
@RestController
@Validated
class HealthCheckController {

    @GetMapping("/health")
    fun checkHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }
}
