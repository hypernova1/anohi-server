package io.hs.anohi.core.exception

import io.hs.anohi.core.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class UnauthorizedException(errorCode: ErrorCode): HttpException(401, errorCode)