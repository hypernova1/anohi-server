package io.hs.anohi.post.infra

import io.hs.anohi.post.domain.Emotion
import io.hs.anohi.post.domain.EmotionRepository
import io.hs.anohi.post.ui.payload.EmotionStatistics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmotionJpaRepository: EmotionRepository, JpaRepository<Emotion, Long> {
    @Query("SELECT emotion_id AS id, count(*) as numberOfPosts FROM post WHERE account_id = :accountId AND deleted_at IS NULL GROUP BY emotion_id", nativeQuery = true)
    override fun findByAccountWithNumberOfPosts(accountId: Long): List<EmotionStatistics>
}