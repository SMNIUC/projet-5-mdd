package com.openclassrooms.mddapi.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String content;
}
