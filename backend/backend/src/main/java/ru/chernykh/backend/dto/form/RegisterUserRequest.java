package ru.chernykh.backend.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserRequest(@NotBlank String name,
                                  @NotBlank String surname,
                                  @Pattern(regexp = "^\\+7 \\([0-9]{3}\\) [0-9]{3}-[0-9]{2}-[0-9]{2}") String phone,
                                  @Email String email,
                                  @NotBlank String password) {}
