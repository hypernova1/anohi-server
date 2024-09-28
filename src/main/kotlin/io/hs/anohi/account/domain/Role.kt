package io.hs.anohi.account.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val name: RoleName = RoleName.ROLE_USER,

) : AuditEntity()