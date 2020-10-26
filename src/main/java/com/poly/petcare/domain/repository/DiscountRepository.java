package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
}
