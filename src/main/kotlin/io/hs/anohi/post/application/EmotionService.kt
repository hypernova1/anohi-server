package io.hs.anohi.post.application

import io.hs.anohi.account.domain.Account
import io.hs.anohi.post.domain.Emotion
import io.hs.anohi.post.domain.EmotionRepository
import io.hs.anohi.post.ui.payload.EmotionStatistics
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
@Transactional
class EmotionService(private val emotionRepository: EmotionRepository) {

    fun getEmotionsStatistics(account: Account): List<EmotionStatistics> {
        return this.emotionRepository.findByAccountWithNumberOfPosts(account.id)
    }

    fun findOne(id: Long): Optional<Emotion> {
        return this.emotionRepository.findById(id)
    }

    fun exists(id: Long): Boolean {
        return this.emotionRepository.existsById(id)
    }

}