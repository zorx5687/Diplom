package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EditPhoneRequest(@NotNull Long id, @Pattern(regexp = "^\\+7 \\([0-9]{3}\\) [0-9]{3}-[0-9]{2}-[0-9]{2}")  String phone) {
}
