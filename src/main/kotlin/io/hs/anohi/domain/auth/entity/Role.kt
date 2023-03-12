package io.hs.anohi.domain.auth.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.auth.constant.RoleName
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