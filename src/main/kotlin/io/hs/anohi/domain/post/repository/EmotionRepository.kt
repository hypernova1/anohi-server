package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.post.entity.Emotion
import org.springframework.data.jpa.repository.JpaRepository

interface EmotionRepository: JpaRepository<Emotion, Long> {
}