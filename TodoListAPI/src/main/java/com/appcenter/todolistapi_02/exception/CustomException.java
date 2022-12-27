package com.appcenter.todolistapi_02.exception;

import com.appcenter.todolistapi_02.common.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    StatusCode statusCode; //상태코드와 메세지 모두 담겨있음
}
