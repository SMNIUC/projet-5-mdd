package com.openclassrooms.mddapi.presentation.controller;

import com.openclassrooms.mddapi.application.service.UserService;
import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.presentation.dto.AuthResponse;
import com.openclassrooms.mddapi.presentation.dto.LoginRequest;
import com.openclassrooms.mddapi.presentation.dto.RegisterRequest;
import com.openclassrooms.mddapi.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response) {

        User user = userService.register(request);
        String token = jwtService.generateToken(user.getEmail());
        writeJwtCookie(response, token, 86400);

        return ResponseEntity.ok(new AuthResponse("Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
        );

        String token = jwtService.generateToken(auth.getName());
        writeJwtCookie(response, token, 86400);

        return ResponseEntity.ok(new AuthResponse("Login successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletResponse response) {
        writeJwtCookie(response, "", 0);
        return ResponseEntity.ok(new AuthResponse("Logout successful"));
    }

    private void writeJwtCookie(HttpServletResponse response, String value, long maxAge) {
        ResponseCookie cookie = ResponseCookie.from("jwt", value)
            .httpOnly(true)
            .secure(cookieSecure)
            .path("/")
            .maxAge(maxAge)
            .sameSite("Strict")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
