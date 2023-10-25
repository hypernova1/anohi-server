package io.hs.anohi.infra.security

import io.hs.anohi.domain.account.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.security.auth.login.AccountNotFoundException

@Service
@Transactional
class CustomUserDetailsService(
    @Autowired val accountRepository: AccountRepository) : UserDetailsService {

    override fun loadUserByUsername(uid: String): UserDetails {
        val account = accountRepository.findByUid(uid)
            .orElseThrow { AccountNotFoundException(uid) }

        return UserAccount(account)
    }

}