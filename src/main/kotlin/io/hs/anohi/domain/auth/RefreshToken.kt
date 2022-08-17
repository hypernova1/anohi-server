package io.hs.anohi.domain.auth

import io.hs.anohi.domain.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class RefreshToken(
    @Column
    val token: String,

    @ManyToOne
    val account: Account,
): BaseEntity()