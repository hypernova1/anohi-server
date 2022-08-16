package io.hs.anohi.infra.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "유저가 존재하지 않습니다.")
class AccountNotFoundException: RuntimeException {
    constructor(id: Long) : super("해당하는 계정이 없습니다. id: $id")
    constructor(email: String) : super("해당하는 계정이 없습니다. email: $email")
}