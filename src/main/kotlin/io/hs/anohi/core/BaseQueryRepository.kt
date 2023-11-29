package io.hs.anohi.core

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.hs.anohi.domain.post.entity.QPost
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl
import javax.persistence.EntityManager

open class BaseQueryRepository<T> {
    @Autowired
    protected lateinit var em: EntityManager

    @Autowired
    protected lateinit var query: JPAQueryFactory
    fun checkLastPage(pageable: Pageable, results: MutableList<T>): SliceImpl<T> {
        var hasNext = false

        if (results.size > pageable.pageSize) {
            hasNext = true
            results.removeAt(pageable.pageSize)
        }

        return SliceImpl<T>(results, pageable, hasNext)
    }

    fun ltId(id: Long?, order: String?): BooleanExpression? {
        return if (id == 0L) {
            null
        } else if (order === null || order === "DESC") {
            QBaseEntity.baseEntity.id.lt(id)
        } else {
            QBaseEntity.baseEntity.id.gt(id)

        }
    }
}