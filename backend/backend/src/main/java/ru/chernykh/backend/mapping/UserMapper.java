package ru.chernykh.backend.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.chernykh.backend.dto.UserDto;
import ru.chernykh.backend.dto.form.RegisterUserRequest;
import ru.chernykh.backend.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto userToUserDto(User user);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "id", ignore = true)
    User registerUserRequestToUser(RegisterUserRequest registerUserRequest);
}
