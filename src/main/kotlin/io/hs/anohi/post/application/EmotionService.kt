package io.hs.anohi.post.application

import io.hs.anohi.account.domain.Account
import io.hs.anohi.post.domain.EmotionRepository
import io.hs.anohi.post.ui.payload.EmotionStatistics
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmotionService(private val emotionRepository: EmotionRepository) {

    fun getEmotionsStatistics(account: Account): List<EmotionStatistics> {
        return this.emotionRepository.findByAccountWithNumberOfPosts(account.id)
    }

}