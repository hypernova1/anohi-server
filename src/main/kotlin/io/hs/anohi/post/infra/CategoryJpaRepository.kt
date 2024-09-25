package io.hs.anohi.post.infra

import io.hs.anohi.post.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<Category, Long> {
}