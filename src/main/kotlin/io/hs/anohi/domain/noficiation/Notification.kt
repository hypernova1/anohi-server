package io.hs.anohi.domain.noficiation

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.chat.payload.MessageDto
import javax.persistence.*

@Entity
class Notification: BaseEntity() {

    @Column
    var message: String = ""

    @Enumerated(EnumType.STRING)
    @Column
    var type: NotificationType = NotificationType.NONE

    @ManyToOne
    lateinit var account: Account
    companion object {
        fun of(messageDto: MessageDto, account: Account): Notification {
            val notification = Notification();
            notification.account = account;
            notification.message = messageDto.message
            notification.type = messageDto.type
            return notification
        }
    }
}