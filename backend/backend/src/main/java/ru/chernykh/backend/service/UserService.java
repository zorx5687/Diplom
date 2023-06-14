package ru.chernykh.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chernykh.backend.dto.UserDto;
import ru.chernykh.backend.dto.form.*;
import ru.chernykh.backend.exception.AuthorizationException;
import ru.chernykh.backend.exception.UserCollisionException;
import ru.chernykh.backend.mapping.UserMapper;
import ru.chernykh.backend.model.User;
import ru.chernykh.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    private User findUserBy(String field) {
        Optional<User> user = userRepository.findUserByEmail(field);
        return user.orElse(null);
    }

    public boolean isUserExists(String field) {
        return findUserBy(field) != null;
    }

    private void validateRegisterUserRequest(RegisterUserRequest registerUserRequest) {
        if (isUserExists(registerUserRequest.email())) {
            throw new UserCollisionException("Такой Email уже зарегистрирован");
        }
        if (isUserExists(registerUserRequest.phone())) {
            throw new UserCollisionException("Такой номер уже зарегистрирован");
        }
    }

    public UserDto save(RegisterUserRequest userRequest) {
        validateRegisterUserRequest(userRequest);
        User user = User.builder().name(userRequest.name())
                .surname(userRequest.surname())
                .email(userRequest.email())
                .phone(userRequest.phone())
                .password(passwordEncoder.encode(userRequest.password())).build();
        User save = userRepository.save(user);
        return userMapper.userToUserDto(save);
    }


    public UserDto enter(EnterUserRequest enterUserRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            enterUserRequest.login(),
                            enterUserRequest.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new AuthorizationException("Неверный email/пароль");

        }
        return userMapper.userToUserDto(findUserBy(enterUserRequest.login()));
    }

    public UserDto findUserDtoById(long id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow());
    }

    User findUserById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserDto changeEmail(EditEmailRequest email) {
        User user = userRepository.findById(email.id()).orElseThrow();
        user.setEmail(email.email());
        User save = userRepository.save(user);
        return userMapper.userToUserDto(save);
    }

    public UserDto changePhone(EditPhoneRequest phone) {
        User user = userRepository.findById(phone.id()).orElseThrow();
        user.setPhone(phone.phone());
        User save = userRepository.save(user);
        return userMapper.userToUserDto(save);
    }

    public UserDto changeSurname(EditSurnameRequest surname) {
        User user = userRepository.findById(surname.id()).orElseThrow();
        user.setSurname(surname.surname());
        User save = userRepository.save(user);
        return userMapper.userToUserDto(save);
    }

    public UserDto changeName(EditNameRequest name) {
        User user = userRepository.findById(name.id()).orElseThrow();
        user.setName(name.name());
        User save = userRepository.save(user);
        return userMapper.userToUserDto(save);
    }
}
