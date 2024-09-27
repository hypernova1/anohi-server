package io.hs.anohi.account.domain

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.NotFoundException

enum class SocialType(val domain: String) {
    GOOGLE("google.com"), APPLE("apple.com"), NONE("");

    companion object {
        fun fromDomain(domain: String): SocialType {
            return entries.find { it.domain == domain } ?: throw NotFoundException(ErrorCode.CANNOT_FIND_SOCIAL_DOMAIN)
        }
    }
}