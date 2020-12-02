package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByNameContaining(String name);

    Page<Product> findAllByCodeContainingAndDescriptionShortContaining
            (String code, String desc, Pageable pageable);

    @Query("SELECT p FROM dbo_product p WHERE LOWER(p.name)  LIKE LOWER( CONCAT('%',:name,'%'))")
    List<Product> findByName(@Param("name") String name);

    @Query(value = "select convert(SUBSTRING_INDEX(code,'SP',-1) , UNSIGNED INTEGER) as c from dbo_product where code like '%SP%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();

    @Query(value = "select count(*) from dbo_product",nativeQuery = true)
    Integer totalItem();

}
