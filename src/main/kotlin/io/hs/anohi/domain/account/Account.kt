package io.hs.anohi.domain.account

import io.hs.anohi.domain.auth.Role
import io.hs.anohi.domain.diary.Diary
import io.hs.anohi.domain.BaseEntity
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Account(
    @Column(unique = true)
    var email: String = "",

    @Column
    var name: String = "",

    @Column
    var password: String = "",

    @OneToMany
    var diaries: MutableList<Diary> = mutableListOf(),

    @Column
    var isActive: Boolean,

    @OneToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()


    ): BaseEntity() {
    constructor() : this("", "", "", mutableListOf(), false) {}

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name
        this.password = updateForm.password
    }

    companion object {
        fun from(joinForm: AccountJoinForm): Account {
            val account = Account()
            account.name = joinForm.name;
            account.password = joinForm.password;
            account.email = joinForm.email;
            account.isActive = true;

            return account;
        }
    }

    fun setRole(role: Role) {
        this.roles.add(role);
    }
}