package io.hs.anohi.infra.config

import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Retryable

@EnableRetry
@Configuration
class RetryConfig