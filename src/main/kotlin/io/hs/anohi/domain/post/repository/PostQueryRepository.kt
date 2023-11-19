package io.hs.anohi.domain.post.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.hs.anohi.domain.post.entity.Post
import io.hs.anohi.domain.post.entity.QImage.image
import io.hs.anohi.domain.post.entity.QPost.post
import io.hs.anohi.domain.post.payload.PostPagination
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


@Repository
class PostQueryRepository(private val em: EntityManager, private val query: JPAQueryFactory) {

    fun searchBySlice(
        accountId: Long,
        lastItemId: Long,
        pagination: PostPagination,
        pageable: Pageable
    ): SliceImpl<Post> {
        val query = query.selectFrom(post)
            .leftJoin(post.images, image)

        val whereClause = BooleanBuilder()
        whereClause.and(post.account.id.eq(accountId))
        if (pagination.emotionId != null) {
            whereClause.and(post.emotion.id.eq(pagination.emotionId))
        }

        val ltPostId = ltPostId(lastItemId, pagination.order)
        whereClause.and(ltPostId)

        val results = query.where(whereClause).orderBy(post.id.desc())
            .limit(pageable.pageSize.toLong() + 1)
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }

    private fun ltPostId(postId: Long?, order: String): BooleanExpression? {
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

        if (results.size > pageable.pageSize) {
            hasNext = true
            results.removeAt(pageable.pageSize)
        }

        return SliceImpl<Post>(results, pageable, hasNext)
    }

}