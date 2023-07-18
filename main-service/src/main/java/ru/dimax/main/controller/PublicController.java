package ru.dimax.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.model.dtos.CategoryDto;
import ru.dimax.main.model.dtos.UserDto;
import ru.dimax.main.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final CategoryService categoryService;


    public PublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
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

    @GetMapping("/category/{catId}")
    public ResponseEntity<CategoryDto> getCategory (@PathVariable Long catId) {
        CategoryDto categoryDto = categoryService.getCategory(catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }
}
