package io.hs.anohi.core.exception

import io.hs.anohi.core.ErrorCode

class BadRequestException(errorCode: ErrorCode): HttpException(400, errorCode)
