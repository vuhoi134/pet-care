package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
