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

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = false)
    var content: String = ""

    @Column(nullable = false)
    var hit: Long = 0

    @Column(nullable = false)
    var numberOfLikes: Long = 0

    @ManyToMany
    var tags: MutableList<Tag> = mutableListOf()

    @ManyToMany
    var emotions: MutableList<Emotion> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var images: MutableList<Image> = mutableListOf()

    @ManyToOne
    lateinit var account: Account

    @OneToMany(mappedBy = "post")
    val favoritePosts: MutableList<FavoritePost> = mutableListOf()

    fun update(postUpdateForm: PostUpdateForm, tags: List<Tag>?, emotions: List<Emotion>?) {
        this.title = postUpdateForm.title ?: this.title
        this.content = postUpdateForm.content ?: this.content
        this.tags = (tags ?: mutableListOf()).toMutableList()
        this.emotions = (emotions ?: mutableListOf()).toMutableList()

        setImages(postUpdateForm)
    }

    private fun setImages(postUpdateForm: PostUpdateForm) {
        val imagePaths = postUpdateForm.imagePaths ?: return

        if (imagePaths.isEmpty()) {
            this.images.forEach { it.post = null }
            return
        }

        this.images = mutableListOf(Image.from(imagePaths[0], this))
    }

    companion object {

        fun of(postRequestForm: PostRequestForm, account: Account, tags: List<Tag>, emotions: List<Emotion>): Post {
            val post = Post()
            post.title = postRequestForm.title
            post.content = postRequestForm.content
            post.account = account
            post.tags.addAll(tags)
            post.emotions.addAll(emotions)
            val images = postRequestForm.imagePaths.map { Image.from(it, post) }
            post.images.addAll(images)
            return post
        }
    }
}