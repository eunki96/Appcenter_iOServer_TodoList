package com.appcenter.todolistapi_02.controller;

import com.appcenter.todolistapi_02.domain.Member;
import com.appcenter.todolistapi_02.dto.result.ResultDto;
import com.appcenter.todolistapi_02.dto.todo.TodoCreateResultDto;
import com.appcenter.todolistapi_02.dto.todo.TodoCreateRequestDto;
import com.appcenter.todolistapi_02.dto.todo.TodoResponseDto;
import com.appcenter.todolistapi_02.dto.todo.TodoUpdateRequestDto;
import com.appcenter.todolistapi_02.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Todo")
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Log4j2
public class TodoController {

    private final TodoService todoService;

    @ApiOperation("Todo 생성")
    @PostMapping
    public ResultDto createTodo (@RequestBody @Validated TodoCreateRequestDto todoCreateRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        log.info("memberId = {}, memberName = {}, memberPassword = ****", member.getMemberId(), member.getName());

        return todoService.createTodo(todoCreateRequestDto, member);
    }

    @ApiOperation("Todo 단일 조회")
    @GetMapping("/{todoId}")
    public TodoResponseDto readTodo(@PathVariable Long todoId){
        return todoService.readTodo(todoId);
    }

    @ApiOperation("Todo 수정")
    @PatchMapping("/{todoId}")
    public ResultDto updateTodo(@PathVariable Long todoId, @RequestBody @Validated TodoUpdateRequestDto todoUpdateRequestDto){
        return todoService.updateTodo(todoId, todoUpdateRequestDto);
    }

    @ApiOperation("Todo 삭제")
    @DeleteMapping("/{todoId}")
    public ResultDto deleteTodo(@PathVariable Long todoId){
        return todoService.deleteTodo(todoId);
    }
}
