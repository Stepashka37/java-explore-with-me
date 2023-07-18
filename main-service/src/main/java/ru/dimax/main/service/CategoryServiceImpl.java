package ru.dimax.main.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.main.exception.CategoryNotFoundException;
import ru.dimax.main.exception.UserNotFoundException;
import ru.dimax.main.mapper.CategoryMapper;
import ru.dimax.main.model.Category;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.CategoryDto;
import ru.dimax.main.model.dtos.NewCategoryDto;
import ru.dimax.main.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.CategoryMapper.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = newCategoryToCategory(newCategoryDto);

        try {
            Category saved = categoryRepository.saveAndFlush(category);
            return modelToDto(saved);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id {} not found", id)));
       categoryRepository.updateCategoryName(id, newCategoryDto.getName());
        Category categoryUPD = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id {} not found", id)));
        return modelToDto(categoryUPD);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return categories.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id {} not found", id)));
        return modelToDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id {} not found", id)));
        categoryRepository.deleteById(id);
    }
}
