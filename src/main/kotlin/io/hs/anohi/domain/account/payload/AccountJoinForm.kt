package io.hs.anohi.domain.account.payload

class AccountJoinForm(
    val email: String,
    val name: String,
    var password: String,
)