package io.hs.anohi.domain.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepository: JpaRepository<Account, Long> {

    fun existsByUid(uid: String): Boolean
    fun findByUid(uid: String): Optional<Account>
    fun countByEmail(email: String): Int

}