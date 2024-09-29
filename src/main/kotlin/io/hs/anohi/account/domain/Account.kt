package io.hs.anohi.account.domain

import io.hs.anohi.account.application.payload.AccountUpdateForm
import io.hs.anohi.core.persistence.AuditEntity
import io.hs.anohi.infra.firebase.FirebaseUser
import io.hs.anohi.post.application.payload.ImageDto
import io.hs.anohi.image.Image
import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Table
import org.hibernate.annotations.*

@FilterDef(name = "deletedAccountFilter", parameters = [ParamDef(name = "deletedAt", type = Boolean::class)])
@Filter(name = "deletedAccountFilter", condition = "deleted_at IS NULL OR :deletedAt = true")
@SQLDelete(sql = "UPDATE account SET deleted_at = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_at is null")
@Table(name = "account")
@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    val id: Long = 0,

    @Column(name = "uid", columnDefinition = "varchar", unique = true, nullable = false)
    val uid: String,

    @Column(name = "email", columnDefinition = "varchar", nullable = false)
    val email: String,

    @Column(name = "name", columnDefinition = "varchar", nullable = false)
    var name: String = "",

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType = SocialType.NONE

) : AuditEntity() {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = [CascadeType.ALL])
    var images: MutableList<AccountImage> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
        }

    @Column(name = "description", columnDefinition = "varchar", nullable = true)
    var description: String = ""

    @Column(name = "number_of_visitors", columnDefinition = "integer", nullable = false)
    var numberOfVisitors: Int = 0

    @ManyToMany(cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()

    fun update(updateForm: AccountUpdateForm) {
        this.name = updateForm.name ?: this.name
        if (updateForm.image != null) {
            this.images.add(
                AccountImage(
                    image = Image.from(updateForm.image),
                    account = this,
                    type = AccountImageType.REPRESENTATION,
                )
            )
        }
        this.description = updateForm.description ?: this.description
    }

    fun increaseVisitor() {
        this.numberOfVisitors++
    }

    companion object {
        fun create(firebaseUser: FirebaseUser, role: Role): Account {
            val account = Account(
                uid = firebaseUser.uid,
                name = firebaseUser.name.orEmpty(),
                email = firebaseUser.email,
                socialType = firebaseUser.socialType
            )

            if (firebaseUser.profileImagePath != null) {
                account.images.add(
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
            }

            account.roles.add(role)

            return account
        }
    }

}