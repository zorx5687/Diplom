package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePostRequest(@NotNull Long postId,
                                @NotBlank String title,
                                @NotNull String category,
                                @NotBlank String address,
                                @NotBlank String description,
                                String price,
                                String photoUrl,
                                long authorId) {
}
