package com.appcenter.todolistapi_02.dto.member;

import com.appcenter.todolistapi_02.domain.Member;
import com.appcenter.todolistapi_02.dto.result.ResultDto;
import com.appcenter.todolistapi_02.dto.todo.TodoResponseDto;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    private List<TodoResponseDto> todoList;

    @Builder
    public MemberResponseDto(Member member, List<TodoResponseDto> todoList){
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.email = member.getEmail();
        this.todoList = todoList;
    }
}
