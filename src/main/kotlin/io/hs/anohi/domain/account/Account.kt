package io.hs.anohi.domain.account

import io.hs.anohi.domain.auth.entity.Role
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.post.entity.FavoritePost
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE account SET deleted_at = current_timestamp WHERE id = ?")
class Account: BaseEntity() {

    @Column(unique = true, nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    var password: String = ""

    @OneToMany(mappedBy = "account")
    var posts: MutableList<Post> = mutableListOf()

    @Column(nullable = false)
    var isActive: Boolean = false

    @ManyToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()

    @OneToMany(mappedBy = "account")
    val favoritePosts: MutableList<FavoritePost> = mutableListOf()

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name ?: this.name
        this.password = updateForm.password ?: this.name
    }

    companion object {
        fun from(joinForm: AccountJoinForm): Account {
            val account = Account()
            account.name = joinForm.name
            account.password = joinForm.password
            account.email = joinForm.email
            account.isActive = true

            return account
        }
    }

    fun addRole(role: Role) {
        this.roles.add(role)
    }
}