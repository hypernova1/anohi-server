package io.hs.anohi.post.application

import io.hs.anohi.post.application.payload.EmotionStatistics
import io.hs.anohi.post.domain.Emotion
import io.hs.anohi.post.domain.EmotionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmotionService(private val emotionRepository: EmotionRepository) {

    fun getEmotionsStatistics(accountId: Long): List<EmotionStatistics> {
        return this.emotionRepository.findByAccountWithNumberOfPosts(accountId)
    }

    fun findOne(id: Long): Emotion? {
        return this.emotionRepository.findById(id)
    }

    fun exists(id: Long): Boolean {
        return this.emotionRepository.existsById(id)
    }

}