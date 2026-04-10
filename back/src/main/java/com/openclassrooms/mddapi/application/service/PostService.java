package com.openclassrooms.mddapi.application.service;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.presentation.dto.CreatePostRequest;
import com.openclassrooms.mddapi.presentation.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public List<PostResponse> getFeed(String email, String order) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Set<Long> subscribedTopicIds = subscriptionRepository.findTopicIdsByUserId(user.getId());
        if (subscribedTopicIds.isEmpty()) {
            return List.of();
        }

        Sort sort = "asc".equalsIgnoreCase(order)
            ? Sort.by(Sort.Direction.ASC, "createdAt")
            : Sort.by(Sort.Direction.DESC, "createdAt");

        List<Post> posts = postRepository.findByTopicIdIn(subscribedTopicIds, sort);
        if (posts.isEmpty()) {
            return List.of();
        }

        // Batch-fetch authors and topics to avoid N+1
        Set<Long> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toSet());
        Map<Long, String> usernameById = userRepository.findAllById(authorIds).stream()
            .collect(Collectors.toMap(User::getId, User::getUsername));
        Map<Long, String> topicNameById = topicRepository.findAllById(subscribedTopicIds).stream()
            .collect(Collectors.toMap(Topic::getId, Topic::getName));

        return posts.stream()
            .map(p -> PostResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .content(p.getContent())
                .authorUsername(usernameById.getOrDefault(p.getAuthorId(), "Inconnu"))
                .topicName(topicNameById.getOrDefault(p.getTopicId(), "Inconnu"))
                .createdAt(p.getCreatedAt())
                .build())
            .collect(Collectors.toList());
    }

    public PostResponse createPost(String email, CreatePostRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Topic topic = topicRepository.findById(request.getTopicId())
            .orElseThrow(() -> new TopicNotFoundException(request.getTopicId()));

        Post saved = postRepository.save(Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .authorId(user.getId())
            .topicId(topic.getId())
            .build());

        return PostResponse.builder()
            .id(saved.getId())
            .title(saved.getTitle())
            .content(saved.getContent())
            .authorUsername(user.getUsername())
            .topicName(topic.getName())
            .createdAt(saved.getCreatedAt())
            .build();
    }
}
