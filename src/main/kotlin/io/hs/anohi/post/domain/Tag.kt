package io.hs.anohi.post.domain

import io.hs.anohi.core.AuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE tag SET deleted_at = current_timestamp WHERE id = ?")
@Entity
class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column
    val name: String,

    @ManyToMany(mappedBy = "tags")
    val posts: MutableList<Post> = mutableListOf()
) : AuditEntity() {

    companion object {
        fun create(name: String): Tag {
            return Tag(name = name)
        }
    }
}