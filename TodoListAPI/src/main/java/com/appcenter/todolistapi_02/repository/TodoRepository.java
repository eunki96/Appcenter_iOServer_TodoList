package com.appcenter.todolistapi_02.repository;

import com.appcenter.todolistapi_02.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
