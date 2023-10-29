package io.hs.anohi.domain.account

import io.hs.anohi.domain.auth.entity.Role
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.contants.LoginType
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.post.entity.FavoritePost
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE account SET deleted_at = current_timestamp WHERE id = ?")
class Account: BaseEntity() {

    @Column(unique = true, nullable = false)
    var uid: String = ""

    @Column(nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var loginType: LoginType = LoginType.NONE;

    @Column(nullable = true)
    var profileImageUrl: String = ""

    @Column(nullable = true)
    var description: String = ""

    @Column(nullable = false)
    var numberOfVisitors: Int = 0

    @OneToMany(mappedBy = "account")
    var posts: MutableList<Post> = mutableListOf()

    @Column(nullable = false)
    var isActive: Boolean = false

    @ManyToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()

    @OneToMany(mappedBy = "account")
    val favoritePosts: MutableList<FavoritePost> = mutableListOf()

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.nickname ?: this.name
        this.profileImageUrl = updateForm.profileImageUrl ?: this.profileImageUrl
        this.description = updateForm.description ?: this.description
    }

    companion object {
        fun from(uid: String, email: String, loginType: LoginType, name: String?, profileImageUrl: String?): Account {
            val account = Account()
            account.uid = uid
            account.name = name ?: ""
            account.email = email
            account.isActive = true
            account.profileImageUrl = profileImageUrl ?: ""
            account.loginType = loginType
            return account
        }
    }

    fun addRole(role: Role) {
        this.roles.add(role)
    }
}