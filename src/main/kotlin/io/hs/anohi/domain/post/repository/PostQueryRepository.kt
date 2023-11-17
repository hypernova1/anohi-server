package io.hs.anohi.domain.post.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.domain.post.entity.QImage.image
import io.hs.anohi.domain.post.entity.QPost.post
import io.hs.anohi.domain.post.payload.PostPagination
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.function.router
import javax.persistence.EntityManager


@Repository
class PostQueryRepository(private val em: EntityManager, private val query: JPAQueryFactory) {

    fun searchBySlice(lastItemId: Long, pagination: PostPagination, pageable: Pageable): SliceImpl<Post> {


        val ltPostId = ltPostId(lastItemId, pagination.order)
        val query = query.selectFrom(post)
            .leftJoin(post.images, image)
            .where(ltPostId)

        if (pagination.emotionId != null) {
            query.where(post.emotion.id.eq(pagination.emotionId))
        }

        val results = query.orderBy(post.id.desc())
            .limit(pageable.pageSize.toLong())
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }

    private fun ltPostId(postId: Long?, order: String): BooleanExpression? {
        println(order)
        return if (postId == 0L) {
            null
        } else if (order == "ASC") {
            post.id.gt(postId)
        } else {
            post.id.lt(postId)
        }
    }

    private fun checkLastPage(pageable: Pageable, results: MutableList<Post>): SliceImpl<Post> {
        var hasNext = false

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size > pageable.pageSize) {
            hasNext = true
            results.removeAt(pageable.pageSize)
        }

        return SliceImpl<Post>(results, pageable, hasNext)
    }

}