package io.hs.anohi.infra.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config {

    @Value("\${key.s3AccessKey}")
    private lateinit var accessKey: String

    @Value("\${key.s3SecretKey}")
    private lateinit var secretKey: String

    private val region: String = "ap-northeast-2"

    val bucketName: String = "anohi-production"

    @Bean
    fun amazonS3(): AmazonS3 {
        val awsCredential = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredential))
            .withRegion(region)
            .build()
    }
}