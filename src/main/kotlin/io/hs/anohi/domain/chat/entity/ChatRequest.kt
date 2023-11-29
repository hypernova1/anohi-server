package io.hs.anohi.domain.chat.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.constant.ChatRequestAnswerType
import org.hibernate.annotations.SQLDelete
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
class ChatRequest: BaseEntity() {

    @OneToOne
    lateinit var sender: Account;

    @OneToOne
    lateinit var receiver: Account;

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