package io.hs.anohi.common.ui

import com.google.firebase.auth.FirebaseAuthException
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.ErrorResponse
import io.hs.anohi.core.exception.HttpException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(HttpException::class)
    protected fun handleNotFoundException(e: HttpException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(e.errorCode.code, e.errorCode.message, e.message)
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleHttpMessageNotReadableException(e: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            ErrorCode.REQUIRE_ARGUMENT_NOT_FOUND.code,
            ErrorCode.REQUIRE_ARGUMENT_NOT_FOUND.message,
            "Required request body is missing"
        )
        return ResponseEntity.status(422).body(errorResponse)
    }
    @ExceptionHandler(value = [ConstraintViolationException::class])
    protected fun constrainViolationException(e: ConstraintViolationException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse<Any>(
            ErrorCode.BAD_ARGUMENT_VALUE.code,
            ErrorCode.BAD_ARGUMENT_VALUE.message,
            e.message.toString()
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(value = [FirebaseAuthException::class])
    protected fun handleFirebaseException(e: FirebaseAuthException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse<Any>(
            ErrorCode.BAD_ARGUMENT_VALUE.code,
            ErrorCode.BAD_ARGUMENT_VALUE.message,
            e.message.toString()
        )

        return ResponseEntity.status(401).body(errorResponse)
    }

}