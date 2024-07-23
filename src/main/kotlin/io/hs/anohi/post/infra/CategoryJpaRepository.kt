package io.hs.anohi.post.infra

import jdk.jfr.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<Category, Long> {
}