package com.appcenter.todolistapi_02.exception;

import com.appcenter.todolistapi_02.common.StatusCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private int code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(StatusCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .code(e.getHttpStatus().value())
                        .message(e.getMessage())
                        .build()
                );
    }
}
