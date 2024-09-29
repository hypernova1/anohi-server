package io.hs.anohi.account.domain

import io.hs.anohi.core.persistence.AuditEntity
import jakarta.persistence.*

@Table(name = "role")
@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "integer", nullable = false)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "name", columnDefinition = "varchar", nullable = false)
    val name: RoleName,

) : AuditEntity()