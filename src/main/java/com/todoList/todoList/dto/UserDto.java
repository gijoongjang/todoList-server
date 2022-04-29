package com.todoList.todoList.dto;

import com.todoList.todoList.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;

    public static UserDto toEntity(User user) {
        if(user == null)
            return null;

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
