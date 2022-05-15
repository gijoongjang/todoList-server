package com.todoList.todoList.controller;

import com.todoList.todoList.dto.TodoDto;
import com.todoList.todoList.entity.Todo;
import com.todoList.todoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/addTodo")
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) throws Exception {
        return new ResponseEntity<>(todoService.addTodo(todoDto, user.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/todoList")
    public ResponseEntity<List<Todo>> getTodoList(@AuthenticationPrincipal User user, @RequestParam(required = false) String isCompleted) throws Exception{
        if(isCompleted != null) {
            return new ResponseEntity<>(todoService.readAllByIsCompleted(user.getUsername(), isCompleted), HttpStatus.OK);
        }

        return new ResponseEntity<>(todoService.readAll(user.getUsername()), HttpStatus.OK);
    }
}
