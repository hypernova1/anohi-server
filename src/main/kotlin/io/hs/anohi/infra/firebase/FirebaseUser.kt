package io.hs.anohi.infra.firebase

import io.hs.anohi.account.domain.SocialType

class FirebaseUser(
    val uid: String,
    val email: String,
    val socialType: SocialType,
    val name: String?,
    val profileImagePath: String?
) {
}