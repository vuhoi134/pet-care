package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
}
