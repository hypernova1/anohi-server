package io.hs.anohi.core

enum class ErrorCode(val message: String, val code: String) {
    CANNOT_FOUND_ACCOUNT("계정이 존재하지 않습니다.", "CANNOT_FOUND_ACCOUNT"),
    CANNOT_FOUND_ROLE("권한을 찾을 수 없습니다.", "CANNOT_FOUND_ROLE"),
    CANNOT_FOUND_REFRESH_TOKEN("리프레쉬 토큰이 존재하지 않습니다.", "CANNOT_FOUND_REFRESH_TOKEN"),
    CANNOT_FOUND_POST("글을 찾을 수 없습니다.", "CANNOT_FOUND_POST"),

    CONFLICT_UID("중복된 uid가 존재합니다.", "CONFLICT_EMAIL"),

    INVALID_TOKEN("유효하지 않은 토큰입니다.", "INVALID_TOKEN"),

    REQUIRE_ARGUMENT_NOT_FOUND("필수 파라미터가 누락되었습니다.", "REQUIRE_ARGUMENT_NOT_FOUND"),
    BAD_ARGUMENT_VALUE("파라미터의 값이 올바르지 않습니다.", "BAD_ARGUMENT_VALUE"),
    CANNOT_FOUND_EMOTION("이모션이 존재하지 않습니다", "REQUIRE_ARGUMENT_NOT_FOUND"),
    CANNOT_FOUND_CHAT_REQUEST("채팅 요청 정보가 존재하지 않습니다.", "CANNOT_FOUND_CHAT_REQUEST"),
    EQUAL_ACCOUNT("요청한 유저와 요청을 받은 유저가 같습니다", "EQUAL_ACCOUNT"), ;


}