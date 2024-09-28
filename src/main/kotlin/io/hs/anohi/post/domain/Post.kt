package io.hs.anohi.post.domain

import io.hs.anohi.core.AuditEntity
import io.hs.anohi.post.application.payload.PostRequestForm
import io.hs.anohi.post.application.payload.PostUpdateForm
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE post SET deleted_at = current_timestamp WHERE id = ?")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column(nullable = true)
    var title: String,

    @Column(nullable = false, columnDefinition = "text")
    var content: String = "",

    @Column(nullable = false)
    var hit: Long = 0,

    @Column(nullable = false)
    var numberOfLikes: Long = 0,

    @ManyToMany
    val tags: MutableList<Tag> = mutableListOf(),

    @ManyToOne
    var emotion: Emotion? = null,

    @Column
    val accountId: Long,

    ) : AuditEntity() {

    @ManyToMany(cascade = [CascadeType.ALL])
    var images: MutableList<Image> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
        }

    fun update(postUpdateForm: PostUpdateForm, tags: List<Tag>?, emotion: Emotion?) {
        this.title = postUpdateForm.title ?: this.title
        this.content = postUpdateForm.content ?: this.content
        this.tags.addAll(tags ?: mutableListOf())
        this.emotion = emotion ?: this.emotion

        setImages(postUpdateForm)
    }

    private fun setImages(postUpdateForm: PostUpdateForm) {
        val images = postUpdateForm.images ?: return

        if (images.isEmpty()) {
            this.images = mutableListOf()
            return
        }

        this.images = images.map { Image.from(it) }.toList().toMutableList()
    }

    fun increaseHit() {
        this.hit++
    }

    fun increaseLike() {
        this.numberOfLikes++
    }

    fun decreaseLike() {
        this.numberOfLikes--
    }

    companion object {

        fun of(postRequestForm: PostRequestForm, accountId: Long, tags: List<Tag>, emotion: Emotion?): Post {
            val post = Post(
                title = postRequestForm.title,
                content = postRequestForm.content,
                accountId = accountId,
                emotion = emotion,
            )
            post.tags.addAll(tags)
            val images = postRequestForm.images.map { Image.from(it) }
            post.images.addAll(images)
            return post
        }
    }
}