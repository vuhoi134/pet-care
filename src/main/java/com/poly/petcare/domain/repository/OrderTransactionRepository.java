package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction,Long> {
}
