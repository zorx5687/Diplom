package ru.chernykh.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernykh.backend.dto.PostDto;
import ru.chernykh.backend.dto.UserDto;
import ru.chernykh.backend.dto.form.*;
import ru.chernykh.backend.service.PostService;
import ru.chernykh.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
private final PostService postService;
    public UserController(UserService userService, PostService postService) {this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        UserDto save = userService.save(registerUserRequest);
        return ResponseEntity.ok(new RegisterUserResponse(save));
    }

    @PostMapping("/enter")
    public ResponseEntity<EnterUserResponse> enterUser(@RequestBody @Valid EnterUserRequest enterUserRequest) {
        UserDto user = userService.enter(enterUserRequest);
        return ResponseEntity.ok(new EnterUserResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithPosts> findUserById(@PathVariable("id") long id) {
        UserDto userById = userService.findUserDtoById(id);
        List<PostDto> postsByUser = postService.findPostsByUser(userById.getId()).stream().sorted((a, b) -> b.getCreatedTime().compareTo(a.getCreatedTime())).collect(Collectors.toList());
        System.out.println(userById);
        return ResponseEntity.ok(new UserWithPosts(userById, postsByUser));
    }

    @PutMapping("/edit/name")
    public ResponseEntity<UserDto> changeName(@RequestBody @Valid EditNameRequest name) {
        return ResponseEntity.ok(userService.changeName(name));
    }

    @PutMapping("/edit/surname")
    public ResponseEntity<UserDto> changeSurname(@RequestBody @Valid EditSurnameRequest surname) {
        return ResponseEntity.ok(userService.changeSurname(surname));
    }

    @PutMapping("/edit/phone")
    public ResponseEntity<UserDto> changePhone(@RequestBody @Valid EditPhoneRequest phone) {
        return ResponseEntity.ok(userService.changePhone(phone));
    }

    @PutMapping("/edit/email")
    public ResponseEntity<UserDto> changeEmail(@RequestBody @Valid EditEmailRequest email) {
        return ResponseEntity.ok(userService.changeEmail(email));
    }
}
