package io.hs.anohi.domain.chat.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class ChatRequest: BaseEntity() {
    @OneToOne
    lateinit var sender: Account;

    @OneToOne
    lateinit var receiver: Account;

}