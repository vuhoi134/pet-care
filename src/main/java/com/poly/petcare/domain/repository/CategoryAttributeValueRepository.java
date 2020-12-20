package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryAttributeValueRepository extends JpaRepository<CategoryAttributeValue,Long> {
    List<CategoryAttributeValue> findAllByProduct(Product product);
}
