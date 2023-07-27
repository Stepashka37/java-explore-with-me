package ru.dimax.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.EventShortDto;
import ru.dimax.main.service.category.CategoryService;
import ru.dimax.main.service.compilation.CompilationService;
import ru.dimax.main.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PublicController {

    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;


    public PublicController(CategoryService categoryService, EventService eventService, CompilationService compilationService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.compilationService = compilationService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(defaultValue = "0") String from,
                                                           @RequestParam(defaultValue = "10") String size) {
        Integer fromInt;
        Integer sizeInt;
        try {
            fromInt = Integer.parseInt(from);
            sizeInt = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return  ResponseEntity.badRequest().build();
        }
        List<CategoryDto> categories = categoryService.getCategories(fromInt, sizeInt);
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
                                                                           @RequestParam(defaultValue = "0") String from,
                                                                           @RequestParam(defaultValue = "10") String size,
                                                                           HttpServletRequest request) {

        if (categories == null) categories = new ArrayList<Long>();

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

        List<EventShortDto> events = eventService.getPublishedEventsByFilters(text, categories, paid, startDT, endDT, onlyAvailable, sort, fromInt, sizeInt, request);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventFullDto> getPublishedEventById(@PathVariable Long id, HttpServletRequest request) {
        EventFullDto event = eventService.getPublishedEventById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping("/compilations")
    public ResponseEntity<List<CompilationDto>> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                                   @RequestParam(defaultValue = "0") String from,
                                                                   @RequestParam(defaultValue = "10") String size) {

        Integer fromInt;
        Integer sizeInt;
        try {
            fromInt = Integer.parseInt(from);
            sizeInt = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return  ResponseEntity.badRequest().build();
        }

        List<CompilationDto> compilationDtos = compilationService.getCompilations(pinned, fromInt, sizeInt);

        return ResponseEntity.status(HttpStatus.OK).body(compilationDtos);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Long compId) {

        CompilationDto compilation = compilationService.getCompilationById(compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilation);
    }
}
