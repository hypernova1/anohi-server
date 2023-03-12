package io.hs.anohi.domain.auth

import io.hs.anohi.domain.auth.constant.RoleName
import io.hs.anohi.domain.auth.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}