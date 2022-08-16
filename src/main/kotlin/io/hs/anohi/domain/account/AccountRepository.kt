package io.hs.anohi.domain.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepository: JpaRepository<Account, Long> {

    fun findByEmail(email: String): Optional<Account>;
    fun countByEmail(email: String): Int;

}