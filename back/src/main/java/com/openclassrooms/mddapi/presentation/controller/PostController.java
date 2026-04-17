package com.openclassrooms.mddapi.presentation.controller;

import com.openclassrooms.mddapi.application.service.PostService;
import com.openclassrooms.mddapi.presentation.dto.CreatePostRequest;
import com.openclassrooms.mddapi.presentation.dto.PostDetailResponse;
import com.openclassrooms.mddapi.presentation.dto.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed(
            @RequestParam(defaultValue = "desc") String order,
            Authentication authentication) {
        return ResponseEntity.ok(postService.getFeed(authentication.getName(), order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(postService.createPost(authentication.getName(), request));
    }
}
