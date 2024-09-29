package io.hs.anohi.chat.domain

import io.hs.anohi.core.persistence.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_message SET deleted_at = current_timestamp WHERE id = ?")
@Table(
    name = "chat_message",
    indexes = [
        Index(name = "idx_chat_massage_chat_room_id_idx", columnList = "chat_room_id"),
        Index(name = "idx_chat_massage_sender_id_idx", columnList = "sender_id")
    ]
)
@Entity
class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    val id: Long = 0,

    @Column(name = "chat_room_id", columnDefinition = "bigint", nullable = false)
    val chatRoomId: Long,

    @Column(name = "sender_id", columnDefinition = "bigint", nullable = false)
    val senderId: Long,

    @Column(name = "message", columnDefinition = "varchar", nullable = false)
    val message: String = ""
) : AuditEntity()