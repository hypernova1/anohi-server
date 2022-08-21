package io.hs.anohi.core

import io.hs.anohi.domain.account.payload.AccountUpdateForm
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    @Column(updatable = false)
    var updatedDate: LocalDateTime? = null
}