package ru.chernykh.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chernykh.backend.model.Category;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    Long id;
    String title;
    String address;
    String description;
    Float price;
    String category;
    UserDto author;
    String url;
    LocalDateTime createdTime;
}
