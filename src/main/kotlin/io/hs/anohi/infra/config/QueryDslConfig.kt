package io.hs.anohi.infra.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig(
    @PersistenceContext val em: EntityManager
) {
    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(em)
}