package io.hs.anohi.core.persistence

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.NumberPath
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.SliceImpl

open class BaseQueryRepository<T : AuditEntity> {
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

    fun ltId(id: Long?, order: String?, entityId: NumberPath<Long>): BooleanExpression? {
        return if (id == 0L) {
            null
        } else if (order === null || order === "DESC") {
            entityId.lt(id)
        } else {
            entityId.gt(id)

        }
    }
}