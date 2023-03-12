package io.hs.anohi.core

enum class ErrorCode(val message: String, val code: String) {
    CANNOT_FOUND_ACCOUNT("계정이 존재하지 않습니다.", "CANNOT_FOUND_ACCOUNT"),
    CANNOT_FOUND_ROLE("권한을 찾을 수 없습니다.", "CANNOT_FOUND_ROLE"),
    CANNOT_FOUND_REFRESH_TOKEN("리프레쉬 토큰이 존재하지 않습니다.", "CANNOT_FOUND_REFRESH_TOKEN"),
    CANNOT_FOUND_POST("글을 찾을 수 없습니다.", "CANNOT_FOUND_POST"),

    CONFLICT_EMAIL("중복된 이메일이 존재합니다.", "CONFLICT_EMAIL"),

    INVALID_TOKEN("유효하지 않은 토큰입니다.", "INVALID_TOKEN"),

    REQUIRE_ARGUMENT_NOT_FOUND("필수 파라미터가 누락되었습니다.", "REQUIRE_ARGUMENT_NOT_FOUND"),


}