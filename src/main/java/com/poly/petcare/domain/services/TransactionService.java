package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.dtos.TransactionDTO;
import com.poly.petcare.domain.entites.Supplier;
import com.poly.petcare.domain.entites.Transaction;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionService extends BaseServices{
    public ResponseEntity<?> create(TransactionDTO dto){
        Transaction transaction = new Transaction();
        transaction.setName(dto.getName());
        transaction.setStatus(StatesConstant.ACTIVE);
        transactionRepository.save(transaction);
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<?> edit(Long transactionID){
        Transaction transaction = transactionRepository.findById(transactionID).orElse(null);
        if (Objects.isNull(transaction)){
            throw new ResourceNotFoundException("Not Found");
        }
        transaction.setStatus(StatesConstant.NOTACTIVE);
        transactionRepository.saveAndFlush(transaction);
        return ResponseEntity.ok(true);
    }

}
