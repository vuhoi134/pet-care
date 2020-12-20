package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.entites.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductStoreRepository extends JpaRepository<ProductStore,Long>, JpaSpecificationExecutor<ProductStore> {
    ProductStore findByCodeTag(String codeTag);
    ProductStore findByProducts_Id(long productId);
    List<ProductStore> findAllByProducts_Id(long productId);
}
