package com.appcenter.todolistapi_02.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Sign-in")
public class MemberSignInRequestDto {

    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "아이디", example = "Welcome")
    @Length(max = 10)

    private String memberId;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "비밀번호", example = "******")
    private String password;
}
