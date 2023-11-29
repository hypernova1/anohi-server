package io.hs.anohi.domain.noficiation

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE notification SET deleted_at = current_timestamp WHERE id = ?")
class Notification: BaseEntity() {

    @Column
    var message: String = ""

    @Enumerated(EnumType.STRING)
    @Column
    var type: NotificationType = NotificationType.NONE

    @ManyToOne
    lateinit var account: Account
    companion object {
        fun from(notificationEvent: NotificationEvent): Notification {
            val notification = Notification();
            notification.account = notificationEvent.receiver;
            notification.message = notificationEvent.message.content
            notification.type = notificationEvent.message.type
            return notification
        }
    }
}