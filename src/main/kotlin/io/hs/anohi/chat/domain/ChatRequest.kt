package io.hs.anohi.chat.domain

import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
class ChatRequest(
    @Column
    val senderId: Long,

    @Column
    val receiverId: Long,

    @Column
    @Enumerated(EnumType.STRING)
    var answerStatus: ChatRequestAnswerStatus = ChatRequestAnswerStatus.WAITING
) : BaseEntity() {


    fun isSender(accountId: Long): Boolean {
        return this.senderId == accountId
    }

    fun answer(answerStatus: ChatRequestAnswerStatus) {
        this.answerStatus = answerStatus
    }

    fun isWaiting(): Boolean {
        return this.answerStatus == ChatRequestAnswerStatus.WAITING
    }

    companion object {
        fun of(senderId: Long, receiverId: Long): ChatRequest {
            return ChatRequest(senderId = senderId, receiverId = receiverId)
        }
    }

}