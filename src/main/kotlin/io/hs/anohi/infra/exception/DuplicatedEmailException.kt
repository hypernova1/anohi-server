package io.hs.anohi.infra.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT, reason = "이미 존재하는 이메일입니다.")
class DuplicatedEmailException: RuntimeException()