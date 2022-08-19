package io.hs.anohi.core

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    protected fun handleNotFoundException(e: RuntimeException) {
        println(e)
    }

}