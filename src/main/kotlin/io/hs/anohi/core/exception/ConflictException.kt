package io.hs.anohi.core.exception

import io.hs.anohi.core.ErrorCode

class ConflictException(errorCode: ErrorCode): HttpException(409, errorCode)