package io.hs.anohi.infra.exception

import io.hs.anohi.core.ErrorCode

class ConflictException(errorCode: ErrorCode): HttpException(409, errorCode)