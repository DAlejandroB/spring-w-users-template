package com.example.template.controllers;

import com.example.template.dtos.UserDto;
import com.example.template.models.User;
import com.example.template.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.createUser(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getBirthday(),
                userDto.getRoles()
        );
        return ResponseEntity.ok(user);
    }
}
