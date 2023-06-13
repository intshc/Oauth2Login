package com.example.oauth2login.controller;

import com.example.oauth2login.domain.User;
import com.example.oauth2login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @RequestMapping("/users")
    public Iterable<User> getUser() {
        return userRepository.findAll();

    }
}
