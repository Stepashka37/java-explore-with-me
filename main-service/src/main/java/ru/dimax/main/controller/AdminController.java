package ru.dimax.main.controller;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;
import ru.dimax.main.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam List<Long> ids,
                                                    @RequestParam(defaultValue = "0") String from,
                                                    @RequestParam(defaultValue = "10") String size) {
        if (ids == null) ids = new ArrayList<Long>();
        Integer fromInt;
        Integer sizeInt;
        try {
            fromInt = Integer.parseInt(from);
            sizeInt = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return  ResponseEntity.badRequest().build();
        }
        List<UserDto> users = userService.getUsers(ids, fromInt, sizeInt);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        UserDto user = userService.createUser(newUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
