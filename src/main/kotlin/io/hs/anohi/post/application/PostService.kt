package io.hs.anohi.post.application

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.Page
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.post.application.payload.PostDetail
import io.hs.anohi.post.application.payload.PostPagination
import io.hs.anohi.post.application.payload.PostRequestForm
import io.hs.anohi.post.application.payload.PostUpdateForm
import io.hs.anohi.post.domain.*
import io.hs.anohi.post.infra.PostQueryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
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
        val emotion = postRequestForm.emotionId?.let {
            emotionService.findOne(it) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
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

    fun findAll(accountId: Long, pagination: PostPagination): Page<PostDetail> {
        pagination.emotionId?.let {
            if (!emotionService.exists(it)) {
                throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
            }
        }

        val searchBySlice = this.postQueryRepository.findAll(
            accountId,
            pagination,
            PageRequest.ofSize(pagination.size)
        )

        val postDtos = searchBySlice.content.map { PostDetail(it) }
        return Page.load(searchBySlice, postDtos)
    }

    @Transactional
    fun findById(id: Long, accountId: Long): PostDetail {
        val post = postRepository.findById(id) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_POST)

        if (accountId != post.accountId) {
            post.increaseHit()
        }

        return PostDetail(post)
    }

    @Transactional
    fun update(id: Long, postUpdateForm: PostUpdateForm, accountId: Long): PostDetail {
        val emotion = postUpdateForm.emotionId?.let {
            emotionService.findOne(it) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
        }

        val tags: List<Tag> = if (postUpdateForm.tags != null) {
            tagService.findAllOrCreate(postUpdateForm.tags)
        } else {
            mutableListOf()
        }

        val post =
            postRepository.findByIdAndAccountId(id, accountId) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_POST)

        post.update(postUpdateForm, tags = tags, emotion = emotion)

        return PostDetail(post)
    }

    @Transactional
    fun registerFavorite(id: Long, accountId: Long) {
        val post = postRepository.findById(id) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_POST)

        val exist = favoritePostRepository.existsByPostAndAccountId(post, accountId)
        if (exist) {
            favoritePostRepository.deleteByPostAndAccountId(post, accountId)
            post.decreaseLike()
            return
        }
        val favoritePost = FavoritePost.of(post, accountId)
        favoritePostRepository.save(favoritePost)
        post.increaseLike()
    }

    fun findByUserId(accountId: Long, pagination: PostPagination): Page<PostDetail> {
        pagination.emotionId?.let {
            if (!emotionService.exists(it)) {
                throw NotFoundException(ErrorCode.CANNOT_FOUND_EMOTION)
            }
        }

        val searchBySlice = this.postQueryRepository.findAllByAccount(
            accountId,
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