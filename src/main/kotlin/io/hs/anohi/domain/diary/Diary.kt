package io.hs.anohi.domain.diary

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE diary SET deleted_at = current_timestamp WHERE id = ?")
class Diary(
    var title: String = "",
    var content: String = "",
    @ManyToOne
    var account: Account,
): BaseEntity()