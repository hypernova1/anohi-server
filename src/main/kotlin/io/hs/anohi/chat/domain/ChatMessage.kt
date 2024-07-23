package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE chat_message SET deleted_at = current_timestamp WHERE id = ?")
class ChatMessage: BaseEntity() {
    @ManyToOne
    lateinit var chatRoom: ChatRoom

    @ManyToOne
    lateinit var sender: Account

    @Column
    val message: String = ""
}