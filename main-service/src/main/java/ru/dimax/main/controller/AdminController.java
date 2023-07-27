package ru.dimax.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.category.NewCategoryDto;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.compilation.NewCompilationDto;
import ru.dimax.main.model.dtos.compilation.UpdateCompilationRequest;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.UpdateEventAdminRequest;
import ru.dimax.main.model.dtos.user.NewUserRequest;
import ru.dimax.main.model.dtos.user.UserDto;
import ru.dimax.main.service.category.CategoryService;
import ru.dimax.main.service.compilation.CompilationService;
import ru.dimax.main.service.event.EventService;
import ru.dimax.main.service.user.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    public AdminController(UserService userService, CategoryService categoryService, EventService eventService, CompilationService compilationService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.compilationService = compilationService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) List<Long> ids,
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

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        CategoryDto category = categoryService.createCategory(newCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long catId, @Valid @RequestBody NewCategoryDto newCategoryDto) {
        CategoryDto category  = categoryService.updateCategory(catId, newCategoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventFullDto>> searchEvents(@RequestParam(required = false) List<Long> users,
                                                           @RequestParam(required = false) List<String> states,
                                                           @RequestParam(required = false) List<Long> categories,
                                                           @RequestParam(required = false) String rangeStart,
                                                           @RequestParam(required = false) String rangeEnd,
                                                           @RequestParam(defaultValue = "0") String from,
                                                           @RequestParam(defaultValue = "10") String size) {
        /*if (users == null) users = new ArrayList<Long>();
        if (states == null) states = new ArrayList<String>();
        if (categories == null) categories = new ArrayList<Long>();*/

        Integer fromInt;
        Integer sizeInt;
        try {
            fromInt = Integer.parseInt(from);
            sizeInt = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return  ResponseEntity.badRequest().build();
        }

        LocalDateTime startDT = LocalDateTime.now().minusDays(1);
        LocalDateTime endDT = LocalDateTime.now().plusMonths(6);
        if (rangeStart != null && rangeEnd != null) {
            try {
                startDT = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                endDT = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        List<EventFullDto> events = eventService.searchEvents(users, states, categories, startDT, endDT, fromInt, sizeInt);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @PatchMapping("events/{eventId}")
    public ResponseEntity<EventFullDto> updateEventByAdmin(@PathVariable Long eventId,
                                                           @Valid @RequestBody UpdateEventAdminRequest request) {
        EventFullDto event = eventService.updateEventByAdmin(eventId, request);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @PostMapping("/compilations")
    public ResponseEntity<CompilationDto> addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.addCompilation(newCompilationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable Long compId,
                                                            @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = compilationService.updateCompilation(compId, updateCompilationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(compilationDto);
    }
}



