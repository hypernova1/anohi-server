package io.hs.anohi.core

import org.springframework.data.domain.Slice


data class Page<T>(
    val pageSize: Int = 0,
    val hasNext: Boolean = false,
    val items: List<T>? = null
) {

    companion object {
        fun <T, U> load(slice: Slice<U>, items: List<T>): Page<T> {
            return Page<T>(
                pageSize = slice.size,
                hasNext = slice.hasNext(),
                items = items,
            )
        }
    }

}