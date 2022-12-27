package com.appcenter.todolistapi_02.dto.todo;

import com.appcenter.todolistapi_02.domain.Member;
import com.appcenter.todolistapi_02.domain.Todo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TodoUpdateRequestDto {

    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "제목", example = "TODO")
    @Length(max = 10, message = "제목을 10자 이내로 입력해주세요")
    private String title;

    @ApiModelProperty(position = 2, required = true, dataType = "String", value = "내용", example = "CONTENT")
    @Length(max = 100, message = "내용을 100자 이내로 입력해주세요")
    private String content;

    @ApiModelProperty(position = 3, required = true, dataType = "boolean", value = "완료 여부", example = " ")
    private boolean isCompleted;

    public Todo toEntity(Member member){
        return Todo.builder()
                .title(title)
                .content(content)
                .isCompleted(isCompleted)
                .build();
    }
}
