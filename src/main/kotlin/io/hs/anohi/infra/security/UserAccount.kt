package io.hs.anohi.infra.security

import io.hs.anohi.account.domain.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class UserAccount(val account: Account) :
    User(account.id.toString(), account.uid, account.roles.map { SimpleGrantedAuthority(it.name.toString()) }) {
}