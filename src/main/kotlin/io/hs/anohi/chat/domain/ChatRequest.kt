package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
class ChatRequest: BaseEntity() {

    @OneToOne
    lateinit var sender: Account

    @OneToOne
    lateinit var receiver: Account

    @Column
    @Enumerated(EnumType.STRING)
    var answer: ChatRequestAnswerType = ChatRequestAnswerType.WAITING

    companion object {
        fun of(sender: Account, receiver: Account): ChatRequest {
            val chatRequest = ChatRequest()
            chatRequest.sender = sender
            chatRequest.receiver = receiver
            return chatRequest
        }
    }

}