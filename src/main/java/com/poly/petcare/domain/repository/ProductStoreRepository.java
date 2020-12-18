package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.entites.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductStoreRepository extends JpaRepository<ProductStore,Long>, JpaSpecificationExecutor<ProductStore> {
    ProductStore findByProducts_Id(long productId);
    ProductStore findByCodeTag(String codeTag);
}
