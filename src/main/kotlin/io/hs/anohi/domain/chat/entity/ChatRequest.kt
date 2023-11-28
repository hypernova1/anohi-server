package io.hs.anohi.domain.chat.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class ChatRequest: BaseEntity() {
    @OneToOne
    lateinit var sender: Account;

    @OneToOne
    lateinit var receiver: Account;

    @Column
    var accept: Boolean = false

    companion object {
        fun of(sender: Account, receiver: Account): ChatRequest {
            val chatRequest = ChatRequest()
            chatRequest.sender = sender
            chatRequest.receiver = receiver
            return chatRequest
        }
    }

}