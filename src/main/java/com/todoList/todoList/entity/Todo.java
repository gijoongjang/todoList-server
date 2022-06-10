package com.todoList.todoList.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String createdDate;

    private String updatedDate;

    private String username;

    private boolean isCompleted;
}
