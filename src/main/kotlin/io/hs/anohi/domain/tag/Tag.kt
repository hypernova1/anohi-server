package io.hs.anohi.domain.tag

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.post.entity.Post
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE tag SET deleted_at = current_timestamp WHERE id = ?")
class Tag: BaseEntity() {
    @Column
    var name: String = "";

    @ManyToMany(mappedBy = "tags")
    val posts: MutableList<Post> = mutableListOf()

    companion object {
        fun create(name: String): Tag {
            val tag = Tag();
            tag.name = name
            return tag;
        }
    }
}