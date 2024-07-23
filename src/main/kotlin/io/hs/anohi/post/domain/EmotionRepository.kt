package io.hs.anohi.post.domain

import io.hs.anohi.post.ui.payload.EmotionStatistics
import java.util.*

interface EmotionRepository {
    fun findByAccountWithNumberOfPosts(accountId: Long): List<EmotionStatistics>
    fun existsById(id: Long): Boolean
    fun findById(id: Long): Optional<Emotion>
}