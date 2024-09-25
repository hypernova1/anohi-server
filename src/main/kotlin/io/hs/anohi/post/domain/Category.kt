package io.hs.anohi.post.domain

import io.hs.anohi.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE category SET deleted_at = current_timestamp WHERE id = ?")
class Category: BaseEntity() {
    @Column
    val name: String = "";

}