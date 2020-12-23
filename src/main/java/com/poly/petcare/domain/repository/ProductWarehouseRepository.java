package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse,Long>, JpaSpecificationExecutor<ProductWarehouse> {
    ProductWarehouse findByCodeTag(String codeTag);
    ProductWarehouse findByExpiryDate(Long expiryDate);
    ProductWarehouse findByProducts_Id(Long id);
    ProductWarehouse findByProducts_IdAndExpiryDate(Long id,Long expiryDate);
}
