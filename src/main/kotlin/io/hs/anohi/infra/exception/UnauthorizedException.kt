package io.hs.anohi.infra.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED, reason = "권한이 없습니다.")
class UnauthorizedException: RuntimeException()