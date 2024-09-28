package io.hs.anohi.post.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE category SET deleted_at = current_timestamp WHERE id = ?")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0,

    @Column
    val name: String = ""
) : AuditEntity()