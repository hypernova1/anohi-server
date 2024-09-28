package io.hs.anohi.common.ui

import io.hs.anohi.common.application.PreSignedUrlService
import io.hs.anohi.common.ui.payloads.PreSignedUrlRequest
import io.hs.anohi.infra.config.annotations.QueryStringArgumentResolver
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URL

@Api(tags = ["도메인에 관련 없는 API 모음"])
@RestController
class PreSignedUrlController(private val preSignedUrlService: PreSignedUrlService) {

    @ApiOperation("AWS S3 Pre Signed Url 생성")
    @GetMapping("/pre-signed-urls")
    fun getPreSignedUrls(
        @Valid @QueryStringArgumentResolver request: PreSignedUrlRequest
    ): ResponseEntity<List<URL>> {
        val result = preSignedUrlService.getUrls(request.folder, request.extension, request.size)
        return ResponseEntity.ok(result)
    }

}