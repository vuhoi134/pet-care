package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByGuid(String guid);

    List<Order> findByUser(User user, Sort sort);

    @Query(value = "select convert(SUBSTRING_INDEX(code,'ODR',-1) , UNSIGNED INTEGER) as c from dbo_order where code like '%ODR%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();
}
