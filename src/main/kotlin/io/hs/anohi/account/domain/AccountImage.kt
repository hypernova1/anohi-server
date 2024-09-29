package io.hs.anohi.account.domain

import io.hs.anohi.image.Image
import jakarta.persistence.*

@Table(name = "account_image", indexes = [Index(name = "idx_account_id_image_id_idx", columnList = "account_id, image_id")])
@Entity
class AccountImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val account: Account,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "image_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val image: Image,

    @Column
    @Enumerated(EnumType.STRING)
    val type: AccountImageType
) {
}