package io.hs.anohi.core.exception

import io.hs.anohi.core.ErrorCode

class NotFoundException(errorCode: ErrorCode) : HttpException(404, errorCode)