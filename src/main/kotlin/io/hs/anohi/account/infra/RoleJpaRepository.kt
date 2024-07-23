package io.hs.anohi.account.infra

import io.hs.anohi.account.domain.Role
import io.hs.anohi.account.domain.RoleRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoleJpaRepository: RoleRepository, JpaRepository<Role, Long> {
}