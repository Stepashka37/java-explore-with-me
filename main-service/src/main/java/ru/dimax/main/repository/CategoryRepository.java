package ru.dimax.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
