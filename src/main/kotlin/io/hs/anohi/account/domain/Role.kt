package io.hs.anohi.account.domain

import io.hs.anohi.core.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToMany

@Entity
class Role: BaseEntity() {
    @Enumerated(EnumType.STRING)
    var name: RoleName = RoleName.ROLE_USER

    @ManyToMany(mappedBy = "roles")
    var accounts: MutableSet<Account> = HashSet()
}