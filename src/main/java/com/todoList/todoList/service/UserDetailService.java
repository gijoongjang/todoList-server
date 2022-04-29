package com.todoList.todoList.service;

import com.todoList.todoList.entity.User;
import com.todoList.todoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null) {
            return null;
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
    }
}
