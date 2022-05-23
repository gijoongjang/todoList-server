package com.todoList.todoList.service;

import com.todoList.todoList.dto.TodoDto;
import com.todoList.todoList.entity.Todo;
import com.todoList.todoList.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Transactional
    public void addTodo(TodoDto todoDto, String userName) {
        Todo todo = TodoDto.toEntity(todoDto, userName);

        todoRepository.save(todo);
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

        if(todo == null) {
            throw new RuntimeException("todo is not exist");
        }

        todo.setCompleted(!todo.isCompleted());

        return todoRepository.save(todo);
    }

    @Transactional
    public Todo updateTodoById(long id, TodoDto todoDto, String userName) {
        Todo todo = todoRepository.findByUsernameAndId(userName, id);

        if(todo == null) {
            throw new RuntimeException("todo is not exist");
        }

        todo.setTitle(todoDto.getTitle());
        todo.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        return todoRepository.save(todo);
    }

    @Transactional
    public void deleteTodoById(long id, String userName) {
        Todo todo = todoRepository.findByUsernameAndId(userName, id);

        if(todo == null) {
            throw new RuntimeException("Todo not found");
        }

        todoRepository.deleteById(id);
    }
}
