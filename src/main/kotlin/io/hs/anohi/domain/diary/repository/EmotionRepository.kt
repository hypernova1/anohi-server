package io.hs.anohi.domain.diary.repository

import io.hs.anohi.domain.diary.entity.Emotion
import org.springframework.data.jpa.repository.JpaRepository

interface EmotionRepository: JpaRepository<Emotion, Long> {
}