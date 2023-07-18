package ru.dimax.main.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;

@UtilityClass
public class UserMapper {

    public NewUserRequest modelToRequestDto(User user) {
        return NewUserRequest.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public User requestDtoToModel(NewUserRequest newUserRequest) {
        return User.builder()
                .id(0L)
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build();
    }

    public UserDto modelToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }


}
