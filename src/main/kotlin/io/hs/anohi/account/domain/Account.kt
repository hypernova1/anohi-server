package io.hs.anohi.account.domain

import io.hs.anohi.account.application.payload.AccountUpdateForm
import io.hs.anohi.core.persistence.AuditEntity
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
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val uid: String = "",

    @Column(nullable = false)
    val email: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType = SocialType.NONE

) : AuditEntity() {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = [CascadeType.ALL])
    var images: MutableList<AccountImage> = mutableListOf()
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
    var isActive: Boolean = true

    @ManyToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name ?: this.name
        if (updateForm.image != null) {
            this.images = mutableListOf(
                AccountImage(
                    image = Image.from(updateForm.image),
                    account = this,
                    type = AccountImageType.REPRESENTATION,
                )
            )
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
            val account = Account(
                uid = firebaseUser.uid,
                name = firebaseUser.name.orEmpty(),
                email = firebaseUser.email,
                socialType = firebaseUser.socialType
            )

            if (firebaseUser.profileImagePath != null) {
                account.images =
                    mutableListOf(
                        AccountImage(
                            account = account, image = Image.from(
                                ImageDto(
                                    id = null,
                                    path = firebaseUser.profileImagePath,
                                    null,
                                    null,
                                    null
                                )
                            ), type = AccountImageType.REPRESENTATION
                        )

                    )
            } else {
                account.images = mutableListOf()
            }
            account.roles.add(role)
            return account
        }
    }

}