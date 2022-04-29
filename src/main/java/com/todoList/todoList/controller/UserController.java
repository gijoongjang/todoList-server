package com.todoList.todoList.controller;

import com.todoList.todoList.dto.ResponseDto;
import com.todoList.todoList.dto.UserDto;
import com.todoList.todoList.jwt.TokenProvider;
import com.todoList.todoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> authUserLogin(@RequestBody UserDto userDto) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

        if(userDetails == null) {
            return ResponseEntity.ok(ResponseDto.builder().error(true).message("Login fail").build());
        }

        String token = tokenProvider.createToken(userDetails);

        return ResponseEntity.ok(ResponseDto.builder().error(false).message("suceess").token(token).build());
    }
}
