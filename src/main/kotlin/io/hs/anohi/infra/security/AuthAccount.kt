package io.hs.anohi.infra.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

@MustBeDocumented
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
annotation class AuthAccount()
