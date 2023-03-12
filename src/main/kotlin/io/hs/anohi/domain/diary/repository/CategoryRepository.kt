package io.hs.anohi.domain.diary.repository

import io.hs.anohi.domain.diary.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
}