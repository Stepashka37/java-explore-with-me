package ru.dimax.main.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dimax.main.exception.UserNotFoundException;
import ru.dimax.main.mapper.UserMapper;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;
import ru.dimax.main.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.UserMapper.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        int page = from/size;
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = new ArrayList<>();
        if(ids.isEmpty()) {
            users = userRepository.findAll(pageable).getContent();
        } else {
            users = userRepository.findAllByIdIn(ids, pageable);
        }
        return users.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(NewUserRequest dto) {
        User user = requestDtoToModel(dto);

        try {
            User saved = userRepository.saveAndFlush(user);
            return modelToDto(saved);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id {} not found", id)));
        userRepository.deleteById(id);
    }
}
