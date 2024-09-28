package io.hs.anohi.chat.domain

import io.hs.anohi.core.persistence.AuditEntity
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

    val isWaiting: Boolean
        get() = this.answerStatus == ChatRequestAnswerStatus.WAITING

    val isAccepted: Boolean
        get() = this.answerStatus == ChatRequestAnswerStatus.ACCEPT

    fun isSender(accountId: Long): Boolean {
        return this.senderId == accountId
    }

    fun answer(answerStatus: ChatRequestAnswerStatus) {
        this.answerStatus = answerStatus
    }

    companion object {
        fun of(senderId: Long, receiverId: Long): ChatRequest {
            return ChatRequest(senderId = senderId, receiverId = receiverId)
        }
    }

}