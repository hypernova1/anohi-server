package io.hs.anohi.common.application

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import io.hs.anohi.common.constans.FolderType
import io.hs.anohi.infra.config.AmazonS3Config
import org.springframework.stereotype.Service
import java.net.URL
import java.util.Date
import java.util.UUID

@Service
class CommonService(private val amazonS3Config: AmazonS3Config) {

    fun getPreSignedUrl(folder: FolderType, extension: String, size: Int): List<URL> {

        val urls = mutableListOf<URL>()
        for (i: Int in 1..size) {
            val fileName = folder.value + "/" + UUID.randomUUID().toString() + "." + extension
            val expiration = Date()
            var expirationTimeMills = expiration.time
            expirationTimeMills += (3 * 60 * 1000).toLong()
            expiration.time = expirationTimeMills

            val generatePreSignedUrlRequest = GeneratePresignedUrlRequest(amazonS3Config.bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)

            urls.add(amazonS3Config.amazonS3().generatePresignedUrl(generatePreSignedUrlRequest))
        }

        return urls
    }
}