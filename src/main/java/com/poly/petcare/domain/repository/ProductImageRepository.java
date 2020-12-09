package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findAllByProduct_Id(Long productId);
}
