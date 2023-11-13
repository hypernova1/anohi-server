package io.hs.anohi.domain.post.repository

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.entity.Emotion
import io.hs.anohi.domain.post.payload.EmotionStatistics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmotionRepository: JpaRepository<Emotion, Long> {
    @Query("SELECT emotion_id AS id, count(*) as numberOfPosts FROM post WHERE account_id = :accountId GROUP BY emotion_id", nativeQuery = true)
    fun findByAccountWithNumberOfPosts(accountId: Long): List<EmotionStatistics>
}