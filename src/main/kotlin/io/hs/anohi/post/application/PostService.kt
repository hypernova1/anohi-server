package io.hs.anohi.post.application

import io.hs.anohi.account.domain.Account
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Page
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.post.domain.*
import io.hs.anohi.post.infra.PostQueryRepository
import io.hs.anohi.post.application.payload.PostDetail
import io.hs.anohi.post.application.payload.PostPagination
import io.hs.anohi.post.application.payload.PostRequestForm
import io.hs.anohi.post.application.payload.PostUpdateForm
import io.hs.anohi.post.domain.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val tagService: TagService,
    private val emotionService: EmotionService,
    private val favoritePostRepository: FavoritePostRepository,
    private val postQueryRepository: PostQueryRepository,
) {

    /**
     * 글을 등록한다
     *
     * @param postRequestForm 글 정보
     * @param accountId 유저 아이디
     * @return 등록된 글 정보
     * */
    @Transactional
    fun create(postRequestForm: PostRequestForm, accountId: Long): PostDetail {
        var emotion: Emotion? = null
        if (postRequestForm.emotionId != null) {
            emotion = emotionService.findOne(postRequestForm.emotionId)
                .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION) }
        }

        val tags = tagService.findAllOrCreate(postRequestForm.tags)
        val post = Post.of(postRequestForm = postRequestForm, accountId = accountId, emotion = emotion, tags = tags)
        this.postRepository.save(post)
        return PostDetail(post)
    }

    @Transactional
    fun delete(id: Long) {
        postRepository.deleteById(id)
    }

    fun findAll(account: Account, pagination: PostPagination): Page<PostDetail> {
        if (pagination.emotionId !== null) {
            val existsEmotion = this.emotionService.exists(pagination.emotionId!!)
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

        if (account.id != post.accountId) {
            post.increaseHit()
        }

        return PostDetail(post)
    }

    @Transactional
    fun update(id: Long, postUpdateForm: PostUpdateForm, account: Account): PostDetail {
        var emotion: Emotion? = null
        if (postUpdateForm.emotionId != null) {
            emotion = emotionService.findOne(postUpdateForm.emotionId!!)
                .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION) }
        }
        var tags: List<Tag>? = null
        if (postUpdateForm.tags != null) {
            tags = tagService.findAllOrCreate(postUpdateForm.tags!!)
        }

        val post = postRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_POST) }

        post.update(postUpdateForm, tags = tags, emotion = emotion)
        postRepository.save(post)

        return PostDetail(post)
    }

    @Transactional
    fun registerFavorite(id: Long, account: Account) {
        val post = postRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_POST) }

        val exist = favoritePostRepository.existsByPostAndAccount(post, account)
        if (exist) {
            favoritePostRepository.deleteByPostAndAccount(post, account)
            post.decreaseLike()
            return
        }
        val favoritePost = FavoritePost.of(post, account)
        favoritePostRepository.save(favoritePost)
        post.increaseLike()
    }

    fun findByUserId(account: Account, pagination: PostPagination): Page<PostDetail> {
        if (pagination.emotionId !== null) {
            val existsEmotion = this.emotionService.exists(pagination.emotionId!!)
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

    fun count(accountId: Long): Int {
        return this.postRepository.countByAccountId(accountId)
    }

    fun countLikePost(accountId: Long): Int {
        return this.favoritePostRepository.countByAccountId(accountId)
    }

}