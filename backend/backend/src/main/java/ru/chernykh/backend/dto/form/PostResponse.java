package ru.chernykh.backend.dto.form;

import ru.chernykh.backend.dto.PostDto;

import java.util.List;

public record PostResponse(List<PostDto> posts) {
}
