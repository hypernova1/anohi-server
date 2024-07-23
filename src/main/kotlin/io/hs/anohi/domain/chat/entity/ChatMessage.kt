package io.hs.anohi.domain.chat.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
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