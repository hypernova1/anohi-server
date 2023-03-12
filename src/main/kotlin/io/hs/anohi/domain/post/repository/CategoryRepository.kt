package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.post.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
}