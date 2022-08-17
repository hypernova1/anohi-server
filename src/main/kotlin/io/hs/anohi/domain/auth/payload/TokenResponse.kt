package io.hs.anohi.domain.auth.payload

class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer"
) {

}