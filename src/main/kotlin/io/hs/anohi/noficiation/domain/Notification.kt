package io.hs.anohi.noficiation.domain

import io.hs.anohi.core.AuditEntity
import io.hs.anohi.noficiation.application.NotificationEvent
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE notification SET deleted_at = current_timestamp WHERE id = ?")
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Column
    var message: String = "",

    @Enumerated(EnumType.STRING)
    @Column
    var type: NotificationType = NotificationType.NONE,

    @Column
    val accountId: Long
) : AuditEntity() {
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