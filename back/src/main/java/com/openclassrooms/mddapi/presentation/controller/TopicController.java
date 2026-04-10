package com.openclassrooms.mddapi.presentation.controller;

import com.openclassrooms.mddapi.application.service.TopicService;
import com.openclassrooms.mddapi.presentation.dto.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(Authentication authentication) {
        return ResponseEntity.ok(topicService.getAllTopicsForUser(authentication.getName()));
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable Long id, Authentication authentication) {
        topicService.subscribe(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long id, Authentication authentication) {
        topicService.unsubscribe(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
