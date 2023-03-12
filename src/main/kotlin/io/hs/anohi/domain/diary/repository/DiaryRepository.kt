package io.hs.anohi.domain.diary.repository

import io.hs.anohi.domain.diary.entity.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
}