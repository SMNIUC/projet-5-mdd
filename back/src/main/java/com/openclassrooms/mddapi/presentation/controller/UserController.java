package com.openclassrooms.mddapi.presentation.controller;

import com.openclassrooms.mddapi.application.service.UserService;
import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.presentation.dto.UpdateUserRequest;
import com.openclassrooms.mddapi.presentation.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .build());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            @Valid @RequestBody UpdateUserRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(authentication.getName(), request));
    }
}
