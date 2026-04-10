package com.openclassrooms.mddapi.infrastructure.repository;

import com.openclassrooms.mddapi.domain.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
