package io.hs.anohi.domain.post.entity

import io.hs.anohi.core.BaseEntity
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.PostRequestForm
import io.hs.anohi.domain.post.payload.PostUpdateForm
import io.hs.anohi.domain.tag.Tag
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE post SET deleted_at = current_timestamp WHERE id = ?")
class Post: BaseEntity() {

    @Column(nullable = true)
    var title: String = ""

    @Column(nullable = false, columnDefinition = "text")
    var content: String = ""

    @Column(nullable = false)
    var hit: Long = 0

    @Column(nullable = false)
    var numberOfLikes: Long = 0

    @ManyToMany
    var tags: MutableList<Tag> = mutableListOf()

    @ManyToOne
    var emotion: Emotion? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var images: MutableList<Image> = mutableListOf()

    @ManyToOne
    lateinit var account: Account

    @OneToMany(mappedBy = "post")
    val favoritePosts: MutableList<FavoritePost> = mutableListOf()

    fun update(postUpdateForm: PostUpdateForm, tags: List<Tag>?, emotion: Emotion?) {
        this.title = postUpdateForm.title ?: this.title
        this.content = postUpdateForm.content ?: this.content
        this.tags = (tags ?: mutableListOf()).toMutableList()
        this.emotion = emotion ?: this.emotion

        setImages(postUpdateForm)
    }

    private fun setImages(postUpdateForm: PostUpdateForm) {
        val imageUrls = postUpdateForm.imageUrls ?: return

        if (imageUrls.isEmpty()) {
            this.images.forEach { it.post = null }
            return
        }

        this.images = mutableListOf(Image.from(imageUrls[0], this))
    }

    companion object {

        fun of(postRequestForm: PostRequestForm, account: Account, tags: List<Tag>, emotion: Emotion?): Post {
            println(emotion)
            val post = Post()
            post.title = postRequestForm.title
            post.content = postRequestForm.content
            post.account = account
            post.tags.addAll(tags)
            post.emotion = emotion
            val images = postRequestForm.imageUrls.map { Image.from(it, post) }
            post.images.addAll(images)
            return post
        }
    }
}