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
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) throws Exception {
        try{
            todoService.addTodo(todoDto, user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(todoService.readAll(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/todoList")
    public ResponseEntity<List<Todo>> getTodoList(@AuthenticationPrincipal User user, @RequestParam(required = false) String isCompleted) throws Exception{
        if(isCompleted != null) {
            return new ResponseEntity<>(todoService.readAllByIsCompleted(user.getUsername(), isCompleted), HttpStatus.OK);
        }

        return new ResponseEntity<>(todoService.readAll(user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/todoList/{id}/changeIsCompleted")
    public ResponseEntity<Todo> changeIsCompleted(@PathVariable long id, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(todoService.changeIsCompletedById(id, user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(todoService.updateTodoById(id, todoDto, user.getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable long id, @AuthenticationPrincipal User user) {
        try{
            todoService.deleteTodoById(id, user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(todoService.readAll(user.getUsername()), HttpStatus.OK);
    }
}
