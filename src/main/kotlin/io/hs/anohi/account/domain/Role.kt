package io.hs.anohi.account.domain

import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToMany

@Entity
class Role: BaseEntity() {
    @Enumerated(EnumType.STRING)
    var name: RoleName = RoleName.ROLE_USER

    @ManyToMany(mappedBy = "roles")
    var accounts: MutableSet<Account> = HashSet()
}