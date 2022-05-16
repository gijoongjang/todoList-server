package com.todoList.todoList.repository;

import com.todoList.todoList.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUsername(String username);
    List<Todo> findAllByUsernameAndIsCompleted(String username, boolean isCompleted);
    Todo findByUsernameAndId(String username, long Id);
}
