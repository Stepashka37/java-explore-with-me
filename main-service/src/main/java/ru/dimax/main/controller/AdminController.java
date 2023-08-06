package ru.dimax.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.constants.Constants;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.category.NewCategoryDto;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.compilation.NewCompilationDto;
import ru.dimax.main.model.dtos.compilation.UpdateCompilationRequest;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.UpdateEventAdminRequest;
import ru.dimax.main.model.dtos.geolocation.FullGeoLocationDto;
import ru.dimax.main.model.dtos.geolocation.NewGeoLocationDto;
import ru.dimax.main.model.dtos.user.UserDto;
import ru.dimax.main.service.category.CategoryService;
import ru.dimax.main.service.compilation.CompilationService;
import ru.dimax.main.service.event.EventService;
import ru.dimax.main.service.geolocation.GeoLocationService;
import ru.dimax.main.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;
    private final GeoLocationService geoLocationService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) List<Long> ids,
                                                     @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        if (ids == null) ids = new ArrayList<>();

        List<UserDto> users = userService.getUsers(ids, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /*@PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        UserDto user = userService.createUser(newUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }*/

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
                                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                           @Positive @RequestParam(defaultValue = "10") Integer size) {


        LocalDateTime startDT = LocalDateTime.now().minusDays(1);
        LocalDateTime endDT = LocalDateTime.now().plusMonths(6);
        if (rangeStart != null && rangeEnd != null) {
            try {
                startDT = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN));
                endDT = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        List<EventFullDto> events = eventService.searchEvents(users, states, categories, startDT, endDT, from, size);
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

    @PostMapping("/locations")
    public ResponseEntity<FullGeoLocationDto> addGeoLocation(@RequestBody @Valid NewGeoLocationDto newGeoLocationDto) {
        FullGeoLocationDto fullGeoLocationDto = geoLocationService.addGeoLocation(newGeoLocationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fullGeoLocationDto);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<FullGeoLocationDto>> getAllLocations(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        List<FullGeoLocationDto> locations = geoLocationService.getAllLocations(from, size);
        return ResponseEntity.status(HttpStatus.OK).body(locations);
    }

    @GetMapping("/locations/{locationId}/search")
    public ResponseEntity<List<EventFullDto>> searchEventsInLocation(@PathVariable Long locationId) {
        List<EventFullDto> events = eventService.searchEventsInLocation(locationId);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

}



