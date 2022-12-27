package com.appcenter.todolistapi_02.service;

import com.appcenter.todolistapi_02.common.StatusCode;
import com.appcenter.todolistapi_02.domain.Member;
import com.appcenter.todolistapi_02.domain.Todo;
import com.appcenter.todolistapi_02.dto.result.ResultDto;
import com.appcenter.todolistapi_02.dto.todo.TodoCreateResultDto;
import com.appcenter.todolistapi_02.dto.todo.TodoCreateRequestDto;
import com.appcenter.todolistapi_02.dto.todo.TodoResponseDto;
import com.appcenter.todolistapi_02.dto.todo.TodoUpdateRequestDto;
import com.appcenter.todolistapi_02.exception.CustomException;
import com.appcenter.todolistapi_02.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public ResultDto createTodo(TodoCreateRequestDto todoCreateRequestDto, Member member){

        ResultDto resultDto = new ResultDto();

        log.info("[createTodo] Todo 생성을 시도합니다");
        todoRepository.save(todoCreateRequestDto.toEntity(member));
        log.info("[createTodo] Todo 생성을 완료했습니다");

        resultDto.setCode(StatusCode.CREATED.getHttpStatus().value());
        resultDto.setMsg(StatusCode.CREATED.getMessage());

        return resultDto;
    }

    public TodoResponseDto readTodo(Long todoId){
        Todo todo =  todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(StatusCode.TODO_NOT_FOUND)
        );
        return new TodoResponseDto(todo);
    }

    public ResultDto updateTodo(Long todoId, TodoUpdateRequestDto todoUpdateRequestDto){

        ResultDto resultDto = new ResultDto();

        Todo oldTodo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(StatusCode.TODO_NOT_FOUND)
        ) ;
        oldTodo.setTitle(todoUpdateRequestDto.getTitle());
        oldTodo.setContent(todoUpdateRequestDto.getContent());
        oldTodo.setCompleted(todoUpdateRequestDto.isCompleted());

        todoRepository.save(oldTodo);

        resultDto.setCode(StatusCode.OK.getHttpStatus().value());
        resultDto.setMsg(StatusCode.OK.getMessage());

        return resultDto;
    }


    public ResultDto deleteTodo(Long todoId){

        ResultDto resultDto = new ResultDto();

        todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(StatusCode.TODO_NOT_FOUND)
        );
        todoRepository.deleteById(todoId);

        resultDto.setCode(StatusCode.OK.getHttpStatus().value());
        resultDto.setMsg(StatusCode.OK.getMessage());

        return resultDto;
    }
}
