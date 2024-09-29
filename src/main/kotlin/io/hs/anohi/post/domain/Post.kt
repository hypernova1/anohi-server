package io.hs.anohi.post.domain

import io.hs.anohi.core.persistence.AuditEntity
import io.hs.anohi.image.Image
import io.hs.anohi.post.application.payload.PostRequestForm
import io.hs.anohi.post.application.payload.PostUpdateForm
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE post SET deleted_at = current_timestamp WHERE id = ?")
@Table(
    name = "post", indexes = [
        Index(name = "post_account_id_idx", columnList = "account_id")
    ]
)
@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    val id: Long = 0,

    @Column(name = "title", columnDefinition = "varchar", nullable = true)
    var title: String,

    @Column(name = "content", columnDefinition = "text", nullable = false)
    var content: String = "",

    @ColumnDefault("0")
    @Column(name = "hit", columnDefinition = "integer", nullable = false)
    var hit: Long = 0,

    @Column(name = "number_of_likes", columnDefinition = "integer", nullable = false)
    var numberOfLikes: Long = 0,

    @ManyToMany
    val tags: MutableList<Tag> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "emotion_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var emotion: Emotion? = null,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = false)
    val accountId: Long,
) : AuditEntity() {

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var postImages: MutableList<PostImage> = mutableListOf()
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
        this.postImages = images.map { PostImage(post = this, image = Image.from(it)) }.toList().toMutableList()
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
            val images = postRequestForm.images.map { PostImage(post = post, image = Image.from(it)) }
            post.postImages.addAll(images)
            return post
        }
    }
}