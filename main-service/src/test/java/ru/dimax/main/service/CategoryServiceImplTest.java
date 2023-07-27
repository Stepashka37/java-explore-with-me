/*
package ru.dimax.main.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dimax.main.EwmMainService;
import ru.dimax.main.model.Category;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.category.NewCategoryDto;
import ru.dimax.main.repository.CategoryRepository;
import ru.dimax.main.repository.UserRepository;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EwmMainService.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    private Faker faker = new Faker();

    @InjectMocks
    private CategoryServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void itShouldCreateCategory() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Concerts")
                .build();

        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("Concerts")
                .build();

        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);
        // When
        CategoryDto categoryDto = underTest.createCategory(newCategoryDto);
        // Then


        assertThat(categoryDto).isEqualToIgnoringGivenFields(newCategoryDto, "id");
        verify(categoryRepository, times(1)).saveAndFlush(any());

    }

    */
/*@Test
    void itShouldThrowWhenConstraintViolationException() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Concerts")
                .build();

        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("Concerts")
                .build();
        doThrow(new ConstraintViolationException("Error saving category: Constraint violation detected")).when(categoryRepository).saveAndFlush(any(Category.class));
        doThrow(new ConstraintViolationException("Constraint violation exception")).when(categoryRepository).saveAndFlush(any(Category.class));
        when(categoryRepository.saveAndFlush(any(Category.class))).thenThrow();
        // When
        // Then

    }*//*

}*/
