package io.hs.anohi.account.domain

import io.hs.anohi.post.domain.Image
import jakarta.persistence.*

@Entity
class AccountImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account: Account,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "image_id")
    val image: Image,

    @Column
    @Enumerated(EnumType.STRING)
    val type: AccountImageType
) {
}