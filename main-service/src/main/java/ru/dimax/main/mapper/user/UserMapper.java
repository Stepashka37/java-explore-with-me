package ru.dimax.main.mapper.user;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.dimax.main.model.Role;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.user.NewUserRequest;
import ru.dimax.main.model.dtos.user.*;


@UtilityClass
public class UserMapper {


    public User requestDtoToModel(NewUserRequest newUserRequest) {
        return User.builder()
                .id(0L)
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .password(newUserRequest.getPassword())
                .role(Role.USER)
                .build();
    }

    public UserDto modelToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortDto modelToShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }


}
