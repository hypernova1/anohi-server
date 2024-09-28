package io.hs.anohi.infra.security

import io.hs.anohi.account.domain.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.security.auth.login.AccountNotFoundException

@Service
class CustomUserDetailsService(
    @Autowired val accountRepository: AccountRepository
) : UserDetailsService {

    override fun loadUserByUsername(uid: String): UserDetails {
        val account = accountRepository.findByUidAndDeletedAtIsNull(uid) ?: throw AccountNotFoundException(uid)

        return UserAccount(account)
    }

}