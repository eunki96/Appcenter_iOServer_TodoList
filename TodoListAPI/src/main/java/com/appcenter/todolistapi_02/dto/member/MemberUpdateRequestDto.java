package com.appcenter.todolistapi_02.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "수정할 이름", example = "고길동")
    @Length(max = 5, message = "이름을 5자 이내로 입력해주세요")
    private String name;

    @ApiModelProperty(position = 2, required = true, dataType = "int", value = "수정할 나이", example = "99")
    @Positive(message = "나이를 다시 입력해주세요")
    private int age;
}
