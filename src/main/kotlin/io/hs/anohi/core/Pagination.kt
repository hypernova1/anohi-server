package io.hs.anohi.core

import org.springframework.data.domain.Page


data class Pagination<T> (
    var pageSize: Int = 0,
    var totalCount: Long = 0,
    var totalPage: Int = 0,
    var items: List<T>? = null,
) {

    companion object {
        fun <T, U> load(page: Page<U>, items: List<T>): Pagination<T> {
            val pagination = Pagination<T>()
            pagination.pageSize = page.size
            pagination.totalCount = page.totalElements
            pagination.totalPage = page.totalPages;
            pagination.items = items;
            return pagination
        }
    }

}