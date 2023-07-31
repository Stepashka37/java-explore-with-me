package ru.dimax.main.mapper.user;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.user.NewUserRequest;
import ru.dimax.main.model.dtos.user.UserDto;
import ru.dimax.main.model.dtos.user.UserShortDto;

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

    public UserShortDto modelToShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }


}
