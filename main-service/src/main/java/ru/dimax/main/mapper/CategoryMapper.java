package ru.dimax.main.mapper;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.Category;
import ru.dimax.main.model.dtos.CategoryDto;
import ru.dimax.main.model.dtos.NewCategoryDto;

@UtilityClass
public class CategoryMapper {

    public Category newCategoryToCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .id(0L)
                .name(newCategoryDto.getName())
                .build();
    }

    public CategoryDto modelToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
