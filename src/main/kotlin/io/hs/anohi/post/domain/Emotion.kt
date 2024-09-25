package io.hs.anohi.post.domain

import io.hs.anohi.core.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE emotion SET deleted_at = current_timestamp WHERE id = ?")
class Emotion: BaseEntity() {
    @Column
    @Enumerated(EnumType.STRING)
    val name: EmotionType = EmotionType.HAPPY;

    @OneToMany
    val posts: MutableList<Post> = mutableListOf()
}