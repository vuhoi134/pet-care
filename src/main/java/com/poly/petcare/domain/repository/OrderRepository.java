package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByGuid(String guid);

    @Query(value = "select convert(SUBSTRING_INDEX(code,'ODR',-1) , UNSIGNED INTEGER) as c from dbo_order where code like '%ODR%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();
}
