package io.hs.anohi.domain.post.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.post.constant.EmotionType
import io.hs.anohi.domain.post.entity.Post
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE emotion SET deleted_at = current_timestamp WHERE id = ?")
class Emotion: BaseEntity() {
    @Column
    @Enumerated(EnumType.STRING)
    val name: EmotionType = EmotionType.HAPPY;

    @OneToMany
    val posts: MutableList<Post> = mutableListOf()
}