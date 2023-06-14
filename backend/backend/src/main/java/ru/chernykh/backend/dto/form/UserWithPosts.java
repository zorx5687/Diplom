package ru.chernykh.backend.dto.form;

import ru.chernykh.backend.dto.PostDto;
import ru.chernykh.backend.dto.UserDto;

import java.util.List;

public record UserWithPosts(UserDto user, List<PostDto> posts) {
}
