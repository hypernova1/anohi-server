package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_message SET deleted_at = current_timestamp WHERE id = ?")
class ChatMessage: BaseEntity() {
    @ManyToOne
    lateinit var chatRoom: ChatRoom

    @ManyToOne
    lateinit var sender: Account

    @Column
    val message: String = ""
}