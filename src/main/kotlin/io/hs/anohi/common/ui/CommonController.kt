package io.hs.anohi.common.ui

import io.hs.anohi.common.application.CommonService
import io.hs.anohi.common.ui.payloads.PreSignedUrlRequest
import io.hs.anohi.infra.annotations.QueryStringArgumentResolver
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URL
import javax.validation.Valid

@Api(tags = ["도메인에 관련 없는 API 모음"])
@RestController
@Validated
class CommonController(private val commonService: CommonService) {
    @ApiOperation("AWS S3 Pre Signed Url 생성")
    @GetMapping("/pre-signed-urls")
    fun getPreSignedUrls(
        @Valid @QueryStringArgumentResolver request: PreSignedUrlRequest
    ): ResponseEntity<List<URL>> {
        val result = commonService.getPreSignedUrl(request.folder, request.extension, request.size)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/health")
    fun checkHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }
}
