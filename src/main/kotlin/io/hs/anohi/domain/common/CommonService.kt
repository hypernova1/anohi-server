package io.hs.anohi.domain.common

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import io.hs.anohi.infra.config.AmazonS3Config
import org.springframework.stereotype.Service
import java.io.Serializable
import java.util.Date
import java.util.UUID

@Service
class CommonService(private val amazonS3Config: AmazonS3Config) {

    fun getPreSignedUrl(size: Int): Map<String, Serializable>? {
        val fileName = UUID.randomUUID().toString()
        val expiration = Date()
        var expirationTimeMills = expiration.time
        expirationTimeMills += (3 * 60 * 1000).toLong()
        expiration.time = expirationTimeMills

        val generatePreSignedUrlRequest = GeneratePresignedUrlRequest(amazonS3Config.bucketName, fileName)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration)

        return mapOf(
            "preSignUrl" to amazonS3Config.amazonS3().generatePresignedUrl(generatePreSignedUrlRequest),
            "encoderFileName" to fileName
        )
    }
}