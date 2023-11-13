package io.hs.anohi.domain.post

import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.EmotionStatistics
import io.hs.anohi.domain.post.repository.EmotionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmotionService(private val emotionRepository: EmotionRepository) {

    fun getEmotionsStatistics(account: Account): List<EmotionStatistics> {
        return this.emotionRepository.findByAccountWithNumberOfPosts(account.id);
    }

}