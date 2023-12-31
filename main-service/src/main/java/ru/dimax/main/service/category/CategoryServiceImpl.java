package ru.dimax.main.service.category;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.main.exception.EntityNotFoundException;
import ru.dimax.main.model.Category;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.category.NewCategoryDto;
import ru.dimax.main.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.category.CategoryMapper.*;


@Service
@Slf4j
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
            log.info("Created category with id: %s", category.getId());
            return modelToDto(saved);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id {} not found", id)));
        category.setName(newCategoryDto.getName());
        Category categoryUPD = categoryRepository.saveAndFlush(category);
        log.info("Updated category with id: %s", category.getId());
        return modelToDto(categoryUPD);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        log.info("Get categories");
        return categories.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id {} not found", id)));
        log.info("Get category with id: %s", category.getId());
        return modelToDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id {} not found", id)));
        log.info("Delete category with id: %s", category.getId());
        categoryRepository.deleteById(id);
    }
}
