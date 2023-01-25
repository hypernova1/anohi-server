package io.hs.anohi.core

class ErrorResponse<T>(
    val errorCode: String,
    val message: String,
    val result: T
)