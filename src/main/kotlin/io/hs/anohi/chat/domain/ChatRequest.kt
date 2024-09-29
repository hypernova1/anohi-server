package io.hs.anohi.chat.domain

import io.hs.anohi.core.persistence.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
@Table(
    name = "chat_request",
    indexes = [
        Index(name = "idx_chat_request_sender_id_idx", columnList = "sender_id"),
        Index(name = "idx_chat_request_receiver_id_idx", columnList = "receiver_id")
    ]
)
@Entity
class ChatRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column(name = "sender_id", columnDefinition = "bigint", nullable = false)
    val senderId: Long,

    @Column(name = "receiver_id", columnDefinition = "bigint", nullable = false)
    val receiverId: Long,

    @Column(name = "answer_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var answerStatus: ChatRequestAnswerStatus = ChatRequestAnswerStatus.WAITING
) : AuditEntity() {
    val isAccepted: Boolean
        get() = this.answerStatus == ChatRequestAnswerStatus.ACCEPT

    fun answer(answerStatus: ChatRequestAnswerStatus) {
        this.answerStatus = answerStatus
    }

    companion object {
        fun of(senderId: Long, receiverId: Long): ChatRequest {
            return ChatRequest(senderId = senderId, receiverId = receiverId)
        }
    }

}