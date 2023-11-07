package io.hs.anohi.domain.common

import io.hs.anohi.domain.common.constans.FolderType
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable
import java.net.URL

@Api(tags = ["도메인에 관련 없는 API 모음"])
@RestController
class CommonController(private val commonService: CommonService) {
    @ApiOperation("AWS S3 Pre Signed Url 생성")
    @GetMapping("/pre-signed-urls")
    fun getPreSignedUrls(
        @RequestParam() folder: FolderType,
        @RequestParam(required = false, defaultValue = "jpg") extension: String,
        @RequestParam(required = false, defaultValue = "1") size: Int
    ): ResponseEntity<List<URL>> {
        val result = commonService.getPreSignedUrl(folder, extension, size)

        return ResponseEntity.ok(result)
    }
}
