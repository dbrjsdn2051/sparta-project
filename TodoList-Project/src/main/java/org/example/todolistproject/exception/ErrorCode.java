package org.example.todolistproject.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다"),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다"),
    TOKEN_MISSING(400, "Authorization 헤더가 누락되었습니다"),
    FORBIDDEN_ACCESS(403, "허가된 접근이 아닙니다. "),
    WEATHER_NOT_FOUND(404, "날씨정보가 존재하지 않습니다."),
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    REJECT_LOGIN(401, "로그인이 실패하였습니다."),
    MISS_MATCHER_PASSWORD(401, "비밀번호가 일치하지 않습니다.");


    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
