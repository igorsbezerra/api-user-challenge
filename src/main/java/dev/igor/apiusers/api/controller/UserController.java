package dev.igor.apiusers.api.controller;

import dev.igor.apiusers.api.request.UserRequest;
import dev.igor.apiusers.api.response.UserResponse;
import dev.igor.apiusers.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(request));
    }

    @GetMapping("/{document}")
    public ResponseEntity<UserResponse> findByDocument(@PathVariable("document") String document) {
        return ResponseEntity.ok(service.findUserByDocument(document));
    }
}
