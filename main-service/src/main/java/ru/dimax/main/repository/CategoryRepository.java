package ru.dimax.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query("UPDATE Category c SET c.name = :name WHERE c.id = :id")
    void updateCategoryName(@Param("id") Long id, @Param("name") String name);

}
