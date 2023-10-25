package io.hs.anohi.infra.security

import io.hs.anohi.domain.account.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class UserAccount(val account: Account) :
    User(account.email, null, account.roles.map { SimpleGrantedAuthority(it.name.toString()) }) {
}