package io.hs.anohi.domain.post

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Pagination
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.post.entity.Emotion
import io.hs.anohi.domain.post.entity.FavoritePost
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.domain.post.payload.PostDetail
import io.hs.anohi.domain.post.payload.PostRequestForm
import io.hs.anohi.domain.post.payload.PostUpdateForm
import io.hs.anohi.domain.post.repository.EmotionRepository
import io.hs.anohi.domain.post.repository.FavoritePostRepository
import io.hs.anohi.domain.post.repository.PostRepository
import io.hs.anohi.domain.tag.Tag
import io.hs.anohi.domain.tag.TagService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val accountRepository: AccountRepository,
    private val tagService: TagService,
    private val emotionRepository: EmotionRepository,
    private val favoritePostRepository: FavoritePostRepository,
) {

    @Transactional
    fun create(postRequestForm: PostRequestForm, account: Account): PostDetail {
        var emotion: Emotion? = null
        if (postRequestForm.emotionId != null) {
            emotion = emotionRepository.findById(postRequestForm.emotionId)
                .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION) }
        }

        val tags = tagService.findAllOrCreate(postRequestForm.tags)

        val post = Post.of(postRequestForm = postRequestForm, account = account, emotion = emotion, tags = tags)
        return PostDetail(post)
    }

    @Transactional
    fun delete(id: Long) {
        postRepository.deleteById(id)
    }

    fun findAll(account: Account, page: Int, size: Int): Pagination<PostDetail> {
        val pagePosts =
            postRepository.findAllByAccount(account, PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"))
        val postDtos = pagePosts.content.map { PostDetail(it) }
        return Pagination.load(pagePosts, postDtos)
    }

    @Transactional
    fun findById(id: Long, account: Account): PostDetail {
        val post = postRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_POST) }

        if (account.id != post.account.id) {
            post.hit++
            this.postRepository.save(post)
        }

        return PostDetail(post)
    }

    @Transactional
    fun update(id: Long, postUpdateForm: PostUpdateForm, account: Account): Post {
        var emotion: Emotion? = null
        if (postUpdateForm.emotionId != null) {
            emotion = emotionRepository.findById(postUpdateForm.emotionId!!)
                .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION) }
        }
        var tags: List<Tag>? = null
        if (postUpdateForm.tags != null) {
            tags = tagService.findAllOrCreate(postUpdateForm.tags!!)
        }

        val post = postRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_POST) }

        post.update(postUpdateForm, tags = tags, emotion = emotion)
        postRepository.save(post)

        return post
    }

    @Transactional
    fun registerFavorite(id: Long, account: Account) {
        val post = postRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_POST) }

        val exist = favoritePostRepository.existsByPostAndAccount(post, account)
        if (exist) {
            favoritePostRepository.deleteByPostAndAccount(post, account)
            post.numberOfLikes--
        } else {
            val favoritePost = FavoritePost.of(post, account)
            favoritePostRepository.save(favoritePost)
            post.numberOfLikes++
        }

        postRepository.save(post)
    }

    fun findByUserId(userId: Long, page: Int, size: Int): Pagination<PostDetail> {
        val account = this.accountRepository.findById(userId)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }
        return this.findAll(account, page, size)
    }

}