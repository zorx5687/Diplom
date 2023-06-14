package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditEmailRequest(@NotNull Long id, @Email String email) {
}
