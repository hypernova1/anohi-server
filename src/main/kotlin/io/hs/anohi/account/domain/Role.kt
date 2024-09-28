package io.hs.anohi.account.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Enumerated(EnumType.STRING)
    var name: RoleName = RoleName.ROLE_USER,

    @ManyToMany(mappedBy = "roles")
    var accounts: MutableSet<Account> = HashSet()

) : AuditEntity()