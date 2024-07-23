package io.hs.anohi.chat.domain

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE chat_room SET deleted_at = current_timestamp WHERE id = ?")
class ChatRoom: BaseEntity() {
    @ManyToMany
    val accounts: List<Account> = mutableListOf()

    @OneToMany
    val messages: List<ChatMessage> = mutableListOf()
}