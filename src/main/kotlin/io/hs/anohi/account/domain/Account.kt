package io.hs.anohi.account.domain

import io.hs.anohi.account.ui.payload.AccountUpdateForm
import io.hs.anohi.core.BaseEntity
import io.hs.anohi.noficiation.domain.Notification
import io.hs.anohi.post.domain.FavoritePost
import io.hs.anohi.post.domain.Image
import io.hs.anohi.post.domain.Post
import io.hs.anohi.post.ui.payload.ImageDto
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef
import org.hibernate.annotations.SQLDelete
import javax.persistence.*

@Entity
@FilterDef(name = "deletedAccountFilter", parameters = [ParamDef(name = "deletedAt", type = "boolean")])
@Filter(name = "deletedAccountFilter", condition = "deleted_at IS NULL OR :deletedAt = true")
@SQLDelete(sql = "UPDATE account SET deleted_at = current_timestamp WHERE id = ?")
class Account : BaseEntity() {

    @Column(unique = true, nullable = false)
    var uid: String = ""

    @Column(nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var loginType: SocialType = SocialType.NONE;

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var images: MutableList<Image> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
        }

    @ManyToMany
    var backgroundImages: MutableList<Image> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
        }

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

    @OneToMany(mappedBy = "account")
    val notifications: MutableList<Notification> = mutableListOf();

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name ?: this.name
        if (updateForm.image != null) {
            this.images = mutableListOf(Image.from(updateForm.image!!))
        } else {
            this.images = mutableListOf()
        }
        this.description = updateForm.description ?: this.description
    }

    companion object {
        fun from(uid: String, email: String, loginType: SocialType, name: String?, profileImageUrl: String?): Account {
            val account = Account()
            account.uid = uid
            account.name = name.orEmpty()
            account.email = email
            account.isActive = true
            if (profileImageUrl != null) {
                account.images =
                    mutableListOf(Image.from(ImageDto(id = null, path = profileImageUrl, null, null, null)))
            } else {
                account.images = mutableListOf()
            }
            account.loginType = loginType
            return account
        }
    }

    fun addRole(role: Role) {
        this.roles.add(role)
    }
}