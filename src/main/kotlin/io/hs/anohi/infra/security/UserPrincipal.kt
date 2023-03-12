package io.hs.anohi.infra.security

import io.hs.anohi.domain.account.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

data class UserPrincipal(
    val id: Long,
    val email: String,
    private val username: String,
    private val password: String,
    private val authorities: Set<GrantedAuthority>


) : UserDetails {

    override fun getAuthorities() = authorities
    override fun getUsername() = username
    override fun getPassword() = password
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isEnabled() = true

    companion object {
        fun create(user: Account): UserPrincipal {
            val authorities = user.roles.stream()
                .map { SimpleGrantedAuthority(it.name.name) }
                .collect(Collectors.toSet())

            return UserPrincipal(user.id, user.email, user.name, user.password, authorities)
        }
    }
}