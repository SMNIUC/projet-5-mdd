package com.openclassrooms.mddapi.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Le titre est requis")
    private String title;

    @NotBlank(message = "Le contenu est requis")
    private String content;

    @NotNull(message = "Le thème est requis")
    private Long topicId;
}
