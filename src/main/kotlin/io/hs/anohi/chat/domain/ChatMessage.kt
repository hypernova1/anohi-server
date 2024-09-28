package io.hs.anohi.chat.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_message SET deleted_at = current_timestamp WHERE id = ?")
class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @ManyToOne
    val chatRoom: ChatRoom,

    @Column
    val senderId: Long,

    @Column
    val message: String = ""
) : AuditEntity()