package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditNameRequest(@NotNull Long id, @NotBlank String name) {
}
