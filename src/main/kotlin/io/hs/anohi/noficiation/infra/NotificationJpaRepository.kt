package io.hs.anohi.noficiation.infra

import io.hs.anohi.noficiation.domain.Notification
import io.hs.anohi.noficiation.domain.NotificationRepository
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationJpaRepository: NotificationRepository, JpaRepository<Notification, Long> {
}