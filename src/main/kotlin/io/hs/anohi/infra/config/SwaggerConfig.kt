package io.hs.anohi.infra.config

import io.hs.anohi.infra.security.AuthUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .ignoredParameterTypes(AuthUser::class.java)
            .useDefaultResponseMessages(false)
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("io.hs.anohi.domain"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    private fun defaultAuth(): List<SecurityReference?> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Anohi Api Documents")
            .description("아노히 서버 API 문서입니다.")
            .version("1.0")
            .build()
    }
}