package com.appcenter.todolistapi_02.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoCreateResultDto{
    private int code;
    private String msg;
}
