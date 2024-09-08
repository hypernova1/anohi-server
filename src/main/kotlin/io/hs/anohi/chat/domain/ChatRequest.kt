package io.hs.anohi.chat.domain

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE chat_request SET deleted_at = current_timestamp WHERE id = ?")
class ChatRequest: BaseEntity() {

    @OneToOne
    lateinit var sender: Account

    @OneToOne
    lateinit var receiver: Account

    @Column
    @Enumerated(EnumType.STRING)
    var answer: ChatRequestAnswerType = ChatRequestAnswerType.WAITING

    fun isSender(account: Account): Boolean {
        return this.sender == account
    }

    fun answer(answerType: ChatRequestAnswerType) {
        this.answer = answerType;
    }

    companion object {
        fun of(sender: Account, receiver: Account): ChatRequest {
            val chatRequest = ChatRequest()
            chatRequest.sender = sender
            chatRequest.receiver = receiver
            return chatRequest
        }
    }

}