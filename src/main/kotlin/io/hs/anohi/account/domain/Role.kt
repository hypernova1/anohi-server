package io.hs.anohi.account.domain

import io.hs.anohi.core.persistence.AuditEntity
import jakarta.persistence.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val name: RoleName,

) : AuditEntity()