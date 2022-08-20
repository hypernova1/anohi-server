package io.hs.anohi.infra.exception

import io.hs.anohi.core.ErrorCode

class NotFoundException(errorCode: ErrorCode) : HttpException(404, errorCode)