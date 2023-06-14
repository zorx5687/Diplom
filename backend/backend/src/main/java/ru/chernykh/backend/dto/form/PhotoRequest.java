package ru.chernykh.backend.dto.form;

import org.springframework.web.multipart.MultipartFile;

public record PhotoRequest(MultipartFile file, String name) {
}
