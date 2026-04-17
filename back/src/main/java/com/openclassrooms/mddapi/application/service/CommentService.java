package com.openclassrooms.mddapi.application.service;

import com.openclassrooms.mddapi.domain.model.Comment;
import com.openclassrooms.mddapi.exception.PostNotFoundException;
import com.openclassrooms.mddapi.infrastructure.repository.CommentRepository;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;
import com.openclassrooms.mddapi.presentation.dto.CommentResponse;
import com.openclassrooms.mddapi.presentation.dto.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponse addComment(Long postId, String email, CreateCommentRequest request) {
        var user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));

        Comment saved = commentRepository.save(Comment.builder()
            .content(request.getContent())
            .authorId(user.getId())
            .postId(postId)
            .build());

        return CommentResponse.builder()
            .id(saved.getId())
            .content(saved.getContent())
            .authorUsername(user.getUsername())
            .createdAt(saved.getCreatedAt())
            .build();
    }
}
