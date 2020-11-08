package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM dbo_category c WHERE LOWER(c.name)  LIKE LOWER( CONCAT('%',:name,'%'))")
    List<Category> search(@Param("name") String name);

    List<Category> findAllByLevel(Integer level);
}
