package io.hs.anohi.infra.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class WebMvcConfig {

    @Bean
    fun commonsRequestLoggingFilter(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeClientInfo(true)
        filter.setIncludeHeaders(true)
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)

        return filter
    }
}