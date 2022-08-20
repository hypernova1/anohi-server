package io.hs.anohi.infra.exception

import io.hs.anohi.core.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class UnauthorizedException(errorCode: ErrorCode): HttpException(401, errorCode)