package ru.dimax.main.service;

import ru.dimax.main.model.Category;
import ru.dimax.main.model.dtos.CategoryDto;
import ru.dimax.main.model.dtos.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long id);



}
