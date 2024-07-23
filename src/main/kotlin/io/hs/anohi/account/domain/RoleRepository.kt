package io.hs.anohi.account.domain

import java.util.Optional

interface RoleRepository {
    fun findByName(name: RoleName): Optional<Role>
}