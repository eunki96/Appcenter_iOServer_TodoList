package com.appcenter.todolistapi_02.dto.todo;

import com.appcenter.todolistapi_02.domain.Todo;
import lombok.Getter;


@Getter
public class TodoResponseDto {
    private Long todoId;
    private String title;
    private String content;
    private boolean isCompleted;
    private Long idOfMember;

    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isCompleted = todo.isCompleted();
        this.idOfMember = todo.getMember().getId();
    }
}
