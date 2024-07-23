package io.hs.anohi.account.infra

import io.hs.anohi.account.domain.Account
import io.hs.anohi.account.domain.AccountRepository
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository: AccountRepository, JpaRepository<Account, Long> {

}