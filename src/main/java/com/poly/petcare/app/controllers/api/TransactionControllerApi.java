package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.dtos.TransactionDTO;
import com.poly.petcare.domain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/transaction")
public class TransactionControllerApi {
    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@Valid @RequestBody TransactionDTO transactionDTO){
        return transactionService.create(transactionDTO);
    }

    @PutMapping("edit/{transactionID}")
    public ResponseEntity<?> edit(@PathVariable Long transactionID) {
        return transactionService.edit(transactionID);
    }

}
