package io.hs.anohi.core

import io.hs.anohi.core.exception.HttpException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(HttpException::class)
    protected fun handleNotFoundException(e: HttpException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(e.errorCode.code, e.errorCode.message, e.message)
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Any> {

        val errorResponse = ErrorResponse(ErrorCode.REQUIRE_ARGUMENT_NOT_FOUND.code, ErrorCode.REQUIRE_ARGUMENT_NOT_FOUND.message, "Required request body is missing")
        return ResponseEntity.status(422).body(errorResponse)
    }

}