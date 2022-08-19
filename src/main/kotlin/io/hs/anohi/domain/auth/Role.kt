package io.hs.anohi.domain.auth

import io.hs.anohi.core.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Role(
    @Enumerated(EnumType.STRING)
    var name: RoleName,
): BaseEntity()