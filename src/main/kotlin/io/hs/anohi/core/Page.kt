package io.hs.anohi.core

import org.springframework.data.domain.Slice


data class Page<T>(
    var pageSize: Int = 0,
    var hasNext: Boolean = false,
    var items: List<T>? = null
) {

    companion object {
        fun <T, U> load(slice: Slice<U>, items: List<T>): Page<T> {
            val pagination = Page<T>()
            pagination.pageSize = slice.size
            pagination.hasNext = slice.hasNext()
            pagination.items = items;
            return pagination
        }
    }

}