package ru.chernykh.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    LocalDateTime createdTime;
}
