package io.hs.anohi.chat.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
class ChatRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column
    val senderId: Long,

    @Column
    val receiverId: Long,

    @Column
    @Enumerated(EnumType.STRING)
    var answerStatus: ChatRequestAnswerStatus = ChatRequestAnswerStatus.WAITING
) : AuditEntity() {

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