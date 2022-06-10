package com.todoList.todoList.dto;

import com.todoList.todoList.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private String title;
    private String createdDate;
    private String updatedDate;
    private String username;
    private boolean isCompleted;

    public static Todo toEntity(TodoDto todoDto, String userName) {
        return Todo.builder()
                .title(todoDto.getTitle())
                .createdDate(todoDto.getCreatedDate())
                .username(userName)
                .build();
    }
}
