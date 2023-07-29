package ru.dimax.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dimax.main.Constants.Constants;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.EventShortDto;
import ru.dimax.main.service.category.CategoryService;
import ru.dimax.main.service.compilation.CompilationService;
import ru.dimax.main.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicController {

    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;


    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                           @Positive @RequestParam(defaultValue = "10") Integer size) {

        List<CategoryDto> categories = categoryService.getCategories(from, size);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long catId) {
        CategoryDto categoryDto = categoryService.getCategory(catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventShortDto>> getPublishedEventsByFilters(@RequestParam(required = false) String text,
                                                                           @RequestParam(required = false) List<Long> categories,
                                                                           @RequestParam(required = false) Boolean paid,
                                                                           @RequestParam(required = false) String rangeStart,
                                                                           @RequestParam(required = false) String rangeEnd,
                                                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                                           @RequestParam(defaultValue = "VIEWS") String sort,
                                                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                           @Positive @RequestParam(defaultValue = "10") Integer size,
                                                                           HttpServletRequest request) {

        if (categories == null) categories = new ArrayList<Long>();

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

        List<EventShortDto> events = eventService.getPublishedEventsByFilters(text, categories, paid, startDT, endDT, onlyAvailable, sort, from, size, request);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventFullDto> getPublishedEventById(@PathVariable Long id, HttpServletRequest request) {
        EventFullDto event = eventService.getPublishedEventById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping("/compilations")
    public ResponseEntity<List<CompilationDto>> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {

        List<CompilationDto> compilationDtos = compilationService.getCompilations(pinned, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(compilationDtos);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Long compId) {

        CompilationDto compilation = compilationService.getCompilationById(compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilation);
    }
}
