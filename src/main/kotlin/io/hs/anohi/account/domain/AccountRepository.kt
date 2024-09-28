package io.hs.anohi.account.domain

interface AccountRepository {

    fun findById(id: Long): Account?
    fun existsByUid(uid: String): Boolean
    fun findByUidAndDeletedAtIsNull(uid: String): Account?
    fun findByIdAndDeletedAtIsNull(id: Long): Account?
    fun save(account: Account): Account
    fun delete(account: Account)

}