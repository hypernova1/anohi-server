package io.hs.anohi.domain.auth.payload

import javax.validation.constraints.NotBlank

class TokenRequest(
    @NotBlank
    val email: String,

    @NotBlank
    val refreshToken: String
)