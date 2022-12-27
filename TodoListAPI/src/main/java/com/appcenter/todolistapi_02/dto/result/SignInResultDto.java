package com.appcenter.todolistapi_02.dto.result;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto {
    private String token;

    @Builder
    public SignInResultDto(int code, String msg, String token) {
        super(code, msg);
        this.token = token;
    }
}
