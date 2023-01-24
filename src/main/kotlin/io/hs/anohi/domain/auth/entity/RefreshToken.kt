package io.hs.anohi.domain.auth.entity

import io.hs.anohi.core.BaseEntity
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