package io.hs.anohi.domain.post

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Page
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.account.AccountRepository
import io.hs.anohi.domain.post.entity.Emotion
import io.hs.anohi.domain.post.entity.FavoritePost
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.domain.post.payload.PostDetail
import io.hs.anohi.domain.post.payload.PostPagination
import io.hs.anohi.domain.post.payload.PostRequestForm
import io.hs.anohi.domain.post.payload.PostUpdateForm
import io.hs.anohi.domain.post.repository.EmotionRepository
import io.hs.anohi.domain.post.repository.FavoritePostRepository
import io.hs.anohi.domain.post.repository.PostQueryRepository
import io.hs.anohi.domain.post.repository.PostRepository
import io.hs.anohi.domain.tag.Tag
import io.hs.anohi.domain.tag.TagService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val tagService: TagService,
    private val emotionRepository: EmotionRepository,
    private val favoritePostRepository: FavoritePostRepository,
    private val postQueryRepository: PostQueryRepository,
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
        this.postRepository.save(post)
        return PostDetail(post)
    }

    @Transactional
    fun delete(id: Long) {
        postRepository.deleteById(id)
    }

    fun findAll(account: Account, pagination: PostPagination): Page<PostDetail> {
        if (pagination.emotionId !== null) {
            val existsEmotion = this.emotionRepository.existsById(pagination.emotionId!!)
                if (!existsEmotion) {
                    throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
                }
        }
        val searchBySlice = this.postQueryRepository.findAll(
            account.id,
            pagination,
            PageRequest.ofSize(pagination.size)
        )

        val postDtos = searchBySlice.content.map { PostDetail(it) }
        return Page.load(searchBySlice, postDtos)
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

    fun findByUserId(account: Account, pagination: PostPagination): Page<PostDetail> {
        if (pagination.emotionId !== null) {
            val existsEmotion = this.emotionRepository.existsById(pagination.emotionId!!)
            if (!existsEmotion) {
                throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
            }
        }
        val searchBySlice = this.postQueryRepository.findAllByAccount(
            account.id,
            pagination,
            PageRequest.ofSize(pagination.size)
        )

        val postDtos = searchBySlice.content.map { PostDetail(it) }
        return Page.load(searchBySlice, postDtos)
    }


}