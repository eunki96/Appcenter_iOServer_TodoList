package com.appcenter.todolistapi_02.domain;

import com.appcenter.todolistapi_02.common.TimeStamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @NotNull
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Member member;


    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }


    @Builder
    public Todo(Long todoId, String title, String content, boolean isCompleted, Member member){
        this.title = title;
        this.todoId = todoId;
        this.content = content;
        this.isCompleted = false;
        this.member = member;
    }

    public void update(){
        this.isCompleted = !this.isCompleted;
    }
}
