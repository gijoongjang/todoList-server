package com.todoList.todoList.service;

import com.todoList.todoList.dto.TodoDto;
import com.todoList.todoList.entity.Todo;
import com.todoList.todoList.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Transactional
    public Todo addTodo(TodoDto todoDto, String userName) {
        Todo todo = TodoDto.toEntity(todoDto, userName);
        return todoRepository.save(todo);
    }

    public List<Todo> readAll(String username) {
        return todoRepository.findAllByUsername(username);
    }

    public List<Todo> readAllByIsCompleted(String userName, String isCompleted) {
        boolean _isCompleted = isCompleted.equals("true") ? true : false;

        return todoRepository.findAllByUsernameAndIsCompleted(userName, _isCompleted);
    }

    @Transactional
    public Todo changeIsCompletedById(long id, String userName) {
        Todo todo = todoRepository.findByUsernameAndId(userName, id);
        todo.setCompleted(!todo.isCompleted());

        return todoRepository.save(todo);
    }
}
