package ru.dimax.main.service.user;

import ru.dimax.main.model.dtos.user.NewUserRequest;
import ru.dimax.main.model.dtos.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest dto);

    void deleteUser(Long id);
}
