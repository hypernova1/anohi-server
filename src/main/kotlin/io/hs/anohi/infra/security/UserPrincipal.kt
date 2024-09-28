package io.hs.anohi.infra.security

import io.hs.anohi.account.domain.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

data class UserPrincipal(
    val id: Long,
    val email: String,
    val uid: String,
    private val username: String,
    private val authorities: Set<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities() = authorities
    override fun getPassword() = uid
    override fun getUsername() = username
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isEnabled() = true

    companion object {
        fun create(user: Account): UserPrincipal {
            val authorities = user.roles.stream()
                .map { SimpleGrantedAuthority(it.name.name) }
                .collect(Collectors.toSet())

            return UserPrincipal(
                id = user.id,
                email = user.email,
                uid = user.uid,
                username = user.name,
                authorities = authorities
            )
        }
    }
}