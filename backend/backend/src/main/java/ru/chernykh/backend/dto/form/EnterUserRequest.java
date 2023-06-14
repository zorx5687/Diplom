package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.NotBlank;

public record EnterUserRequest(@NotBlank String login, @NotBlank String password) {
}
