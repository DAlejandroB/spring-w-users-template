package com.example.template.controllers;

import com.example.template.dtos.UserDto;
import com.example.template.models.User;
import com.example.template.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
        String passwordHash = bCryptPasswordEncoder.encode(userDto.getPassword());

        User user = userService.createUser(
                userDto.getUsername(),
                userDto.getEmail(),
                passwordHash,
                userDto.getBirthday(),
                userDto.getRoles()
        );
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
