package io.hs.anohi.noficiation.domain

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.noficiation.application.NotificationEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE notification SET deleted_at = current_timestamp WHERE id = ?")
class Notification(
    @Column
    var message: String = "",

    @Enumerated(EnumType.STRING)
    @Column
    var type: NotificationType = NotificationType.NONE,

    @Column
    val accountId: Long
) : BaseEntity() {
    companion object {
        fun from(notificationEvent: NotificationEvent): Notification {
            return Notification(
                accountId = notificationEvent.receiverId,
                message = notificationEvent.message.content,
                type = notificationEvent.message.type
            )
        }
    }
}