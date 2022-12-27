package com.appcenter.todolistapi_02.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {
    OK(HttpStatus.OK, "OK"),

    CREATED(HttpStatus.CREATED, "CREATED"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD REQUEST"),
    ID_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ID BAD REQUEST"),
    PASSWORD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PASSWORD BAD REQUEST"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN"),

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND"),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "TODO_NOT_FOUND"),

    ID_CONFLICT(HttpStatus.CONFLICT, "ID_CONFLICT"),
    EMAIL_CONFLICT(HttpStatus.CONFLICT, "EMAIL_CONFLICT"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");

    private final HttpStatus httpStatus;
    private final String message;
}
