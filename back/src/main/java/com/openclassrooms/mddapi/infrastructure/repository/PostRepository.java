package com.openclassrooms.mddapi.infrastructure.repository;

import com.openclassrooms.mddapi.domain.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTopicIdIn(Collection<Long> topicIds, Sort sort);
}
