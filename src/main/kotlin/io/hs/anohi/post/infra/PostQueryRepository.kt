package io.hs.anohi.post.infra

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import io.hs.anohi.core.BaseQueryRepository
import io.hs.anohi.post.domain.Post
import io.hs.anohi.post.domain.QImage.image
import io.hs.anohi.post.domain.QPost.post
import io.hs.anohi.post.application.payload.PostPagination
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository


@Repository
class PostQueryRepository: BaseQueryRepository<Post>() {

    fun findAllByAccount(
        accountId: Long,
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

        if (pagination.lastItemId != 0L) {
            whereClause.and(ltId(pagination.lastItemId, pagination.order, post.id))
        }

        val results = query.where(whereClause).orderBy(post.id.desc())
            .limit(pageable.pageSize.toLong() + 1)
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }

    fun findAll(
        accountId: Long,
        pagination: PostPagination,
        pageable: Pageable
    ): SliceImpl<Post> {

        val subQuery = query.select(post.account.id, post.id.max().`as`("id"))
            .from(post)
            .groupBy(post.account.id)

        val query = query.selectFrom(post)
            .leftJoin(post.images, image)

        val whereClause = BooleanBuilder()
        whereClause.and(Expressions.list(post.account.id, post.id).`in`(subQuery))
        if (pagination.emotionId != null) {
            whereClause.and(post.emotion.id.eq(pagination.emotionId))
        }

        if (pagination.lastItemId != 0L) {
            whereClause.and(ltId(pagination.lastItemId, pagination.order, post.id))
        }

        val results = query.where(whereClause)
            .limit(pageable.pageSize.toLong() + 1)
            .orderBy(post.id.desc())
            .fetchJoin()
            .fetch()

        return checkLastPage(pageable, results)
    }
}