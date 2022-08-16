package io.hs.anohi.infra.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "권한을 찾을 수 없습니다.")
class RoleNotFoundException: RuntimeException()