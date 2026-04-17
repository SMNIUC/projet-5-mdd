package com.openclassrooms.mddapi.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String topicName;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
}
