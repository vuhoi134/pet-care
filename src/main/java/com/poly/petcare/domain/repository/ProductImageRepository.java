package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
