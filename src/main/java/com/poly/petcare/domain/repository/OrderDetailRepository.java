package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.OrderDetail;
import com.poly.petcare.domain.entites.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>, JpaSpecificationExecutor<OrderDetail> {
    List<OrderDetail> findByOrder(Order order);
}
