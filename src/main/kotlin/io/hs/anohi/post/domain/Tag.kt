package io.hs.anohi.post.domain

import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE tag SET deleted_at = current_timestamp WHERE id = ?")
@Entity
class Tag: BaseEntity() {
    @Column
    var name: String = "";

    @ManyToMany(mappedBy = "tags")
    val posts: MutableList<Post> = mutableListOf()

    companion object {
        fun create(name: String): Tag {
            val tag = Tag()
            tag.name = name
            return tag;
        }
    }
}