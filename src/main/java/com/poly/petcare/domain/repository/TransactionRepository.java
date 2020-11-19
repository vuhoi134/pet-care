package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
