package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByOrder(Order order);
}
