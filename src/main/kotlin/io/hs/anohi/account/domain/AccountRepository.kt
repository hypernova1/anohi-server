package io.hs.anohi.account.domain

import java.util.*

interface AccountRepository {

    fun findById(id: Long): Optional<Account>
    fun existsByUid(uid: String): Boolean
    fun findByUidAndDeletedAtIsNull(uid: String): Optional<Account>
    fun findByIdAndDeletedAtIsNull(id: Long): Optional<Account>
    fun save(account: Account): Account
    fun delete(account: Account)

}