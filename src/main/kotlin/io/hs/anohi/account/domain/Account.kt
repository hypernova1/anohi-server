package io.hs.anohi.account.domain

import io.hs.anohi.account.application.payload.AccountUpdateForm
import io.hs.anohi.core.AuditEntity
import io.hs.anohi.infra.firebase.FirebaseUser
import io.hs.anohi.post.application.payload.ImageDto
import io.hs.anohi.post.domain.Image
import jakarta.persistence.*
import jakarta.persistence.CascadeType
import org.hibernate.annotations.*

@Entity
@FilterDef(name = "deletedAccountFilter", parameters = [ParamDef(name = "deletedAt", type = Boolean::class)])
@Filter(name = "deletedAccountFilter", condition = "deleted_at IS NULL OR :deletedAt = true")
@SQLDelete(sql = "UPDATE account SET deleted_at = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_at is null")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    var uid: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var socialType: SocialType = SocialType.NONE

) : AuditEntity() {


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

    @Column(nullable = false)
    var isActive: Boolean = false

    @ManyToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name ?: this.name
        if (updateForm.image != null) {
            this.images = mutableListOf(Image.from(updateForm.image!!))
        } else {
            this.images = mutableListOf()
        }
        this.description = updateForm.description ?: this.description
    }

    fun increaseVisitor() {
        this.numberOfVisitors++
    }

    companion object {
        fun of(firebaseUser: FirebaseUser, role: Role): Account {
            val account = Account()
            account.uid = firebaseUser.uid
            account.name = firebaseUser.name.orEmpty()
            account.email = firebaseUser.email
            account.isActive = true
            if (firebaseUser.profileImagePath != null) {
                account.images =
                    mutableListOf(
                        Image.from(
                            ImageDto(
                                id = null,
                                path = firebaseUser.profileImagePath,
                                null,
                                null,
                                null
                            )
                        )
                    )
            } else {
                account.images = mutableListOf()
            }
            account.socialType = firebaseUser.socialType
            account.roles.add(role)
            return account
        }
    }

}