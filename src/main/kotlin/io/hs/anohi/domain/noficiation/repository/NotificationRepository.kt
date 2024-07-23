package io.hs.anohi.domain.noficiation.repository

import io.hs.anohi.domain.noficiation.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
}