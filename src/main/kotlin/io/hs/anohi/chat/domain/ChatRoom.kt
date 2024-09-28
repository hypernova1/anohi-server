package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_room SET deleted_at = current_timestamp WHERE id = ?")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @ManyToMany
    val accounts: List<Account> = mutableListOf(),

    @OneToMany
    val messages: List<ChatMessage> = mutableListOf()
) : AuditEntity() {

}