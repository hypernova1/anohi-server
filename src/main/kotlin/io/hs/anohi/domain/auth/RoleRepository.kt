package io.hs.anohi.domain.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}