package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    @Query(value="select count(*) from dbo_product",nativeQuery=true)
    int totalItem();

    @Query("SELECT p FROM dbo_product p WHERE LOWER(p.name)  LIKE LOWER( CONCAT('%',:name,'%'))")
    List<Product> search(@Param("name") String name);

}
