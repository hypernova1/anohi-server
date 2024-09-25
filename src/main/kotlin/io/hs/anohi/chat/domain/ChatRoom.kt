package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_room SET deleted_at = current_timestamp WHERE id = ?")
class ChatRoom: BaseEntity() {
    @ManyToMany
    val accounts: List<Account> = mutableListOf()

    @OneToMany
    val messages: List<ChatMessage> = mutableListOf()
}