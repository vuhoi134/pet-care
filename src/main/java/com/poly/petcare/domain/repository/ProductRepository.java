package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);


    @Query("SELECT p FROM dbo_product p WHERE LOWER(p.name)  LIKE LOWER( CONCAT('%',:name,'%'))")
    List<Product> findByName(@Param("name") String name);


}
