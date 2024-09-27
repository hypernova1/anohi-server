package io.hs.anohi.infra.firebase

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.account.domain.SocialType
import org.springframework.stereotype.Component

@Component
class FirebaseDecoder(private val firebaseAuth: FirebaseAuth) {

    /**
     * 토큰에서 파이어베이스 유저 정보를 가져온다.
     *
     * @param token jwtToken
     * @return 파이어베이스 유저 정보
     * */
    fun parseToken(token: String): FirebaseUser {
        val firebaseToken = this.firebaseAuth.verifyIdToken(token)
        val firebase = firebaseToken.claims["firebase"] as Map<*, *>
        val identities = firebase["identities"] as Map<*, *>
        val identitiesKey: String = identities.keys.toList().first().toString()

        val socialType = SocialType.fromDomain(identitiesKey)
        return FirebaseUser(
            uid =  firebaseToken.uid,
            email = firebaseToken.email,
            name = firebaseToken.name,
            profileImagePath = firebaseToken.picture,
            socialType = socialType
        )
    }

}