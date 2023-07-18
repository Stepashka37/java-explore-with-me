package ru.dimax.main.service;

import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest dto);

    void deleteUser(Long id);
}
