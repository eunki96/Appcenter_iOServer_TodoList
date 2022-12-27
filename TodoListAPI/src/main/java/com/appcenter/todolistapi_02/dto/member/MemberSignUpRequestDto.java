package com.appcenter.todolistapi_02.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequestDto {

    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "아이디", example = "Welcome")
    @Length(max = 10, message = "아이디를 10자 이내로 입력해주세요")
    private String memberId;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "비밀번호", example = "******")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이내여야 합니다")
    private String password;

    @ApiModelProperty(position = 3, required = true, dataType = "String", value = "이름", example = "홍길동")
    @Length(max = 5, message = "이름을 5자 이내로 입력해주세요")
    private String name;

    @ApiModelProperty(position = 4, required = true, dataType = "String", value = "이메일", example = "welcome@inu.ac.kr")
    @Email(message = "이메일 형식이 잘못되었습니다")
    private String email;

    @ApiModelProperty(position = 5, required = true, dataType = "int", value = "나이", example = "1")
    @Positive(message = "나이를 다시 입력해주세요")
    private int age;

    @ApiModelProperty(position = 6, required = true, dataType = "String", value = "권한", example = "USER")
    private String role;
}
