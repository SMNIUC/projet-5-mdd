package com.openclassrooms.mddapi.application.service;

import com.openclassrooms.mddapi.domain.model.Subscription;
import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.infrastructure.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;
import com.openclassrooms.mddapi.presentation.dto.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public List<TopicResponse> getAllTopicsForUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Set<Long> subscribedIds = subscriptionRepository.findTopicIdsByUserId(user.getId());

        return topicRepository.findAll().stream()
            .map(topic -> TopicResponse.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .subscribed(subscribedIds.contains(topic.getId()))
                .build())
            .collect(Collectors.toList());
    }

    public void subscribe(String email, Long topicId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (!topicRepository.existsById(topicId)) {
            throw new TopicNotFoundException(topicId);
        }

        if (!subscriptionRepository.existsByUserIdAndTopicId(user.getId(), topicId)) {
            subscriptionRepository.save(
                Subscription.builder()
                    .userId(user.getId())
                    .topicId(topicId)
                    .build()
            );
        }
    }

    @Transactional
    public void unsubscribe(String email, Long topicId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (!topicRepository.existsById(topicId)) {
            throw new TopicNotFoundException(topicId);
        }

        subscriptionRepository.deleteByUserIdAndTopicId(user.getId(), topicId);
    }
}
