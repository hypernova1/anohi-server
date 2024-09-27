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
    var answerStatus: ChatRequestAnswerStatus = ChatRequestAnswerStatus.WAITING

    fun isSender(account: Account): Boolean {
        return this.sender == account
    }

    fun answer(answerStatus: ChatRequestAnswerStatus) {
        this.answerStatus = answerStatus
    }

    fun isWaiting(): Boolean {
        return this.answerStatus == ChatRequestAnswerStatus.WAITING
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