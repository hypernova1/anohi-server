package io.hs.anohi.domain.diary

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Diary(
    var title: String = "",
    var content: String = "",
    @ManyToOne
    var account: Account,
): BaseEntity()