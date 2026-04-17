package com.openclassrooms.mddapi.application.service;

import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.exception.EmailAlreadyExistsException;
import com.openclassrooms.mddapi.exception.UsernameAlreadyExistsException;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;
import com.openclassrooms.mddapi.presentation.dto.RegisterRequest;
import com.openclassrooms.mddapi.presentation.dto.UpdateUserRequest;
import com.openclassrooms.mddapi.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .or(() -> userRepository.findByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public UserResponse updateUser(String currentEmail, UpdateUserRequest request) {
        User user = userRepository.findByEmail(currentEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentEmail));

        if (request.getEmail() != null && !request.getEmail().isBlank()
                && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email already in use");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getUsername() != null && !request.getUsername().isBlank()
                && !request.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new UsernameAlreadyExistsException("Username already in use");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);
        return UserResponse.builder()
            .id(saved.getId())
            .email(saved.getEmail())
            .username(saved.getUsername())
            .build();
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already in use");
        }

        User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

        return userRepository.save(user);
    }
}
