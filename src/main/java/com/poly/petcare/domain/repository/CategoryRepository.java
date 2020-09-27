package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
