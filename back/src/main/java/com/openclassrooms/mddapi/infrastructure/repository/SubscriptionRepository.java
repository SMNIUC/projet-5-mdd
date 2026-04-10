package com.openclassrooms.mddapi.infrastructure.repository;

import com.openclassrooms.mddapi.domain.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.topicId FROM Subscription s WHERE s.userId = :userId")
    Set<Long> findTopicIdsByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndTopicId(Long userId, Long topicId);

    void deleteByUserIdAndTopicId(Long userId, Long topicId);
}
