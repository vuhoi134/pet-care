package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.domain.entites.Supplier;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SupplierService extends BaseServices{
    public ResponseEntity<?> create(SupplierDTO dto){
        Supplier supplier = new Supplier();
        supplier.setCode(dto.getCode());
        supplier.setAddress(dto.getAddress());
        supplier.setName(dto.getName());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplierRepository.save(supplier);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> delete (Long supplierID){
        Supplier supplier = supplierRepository.findById(supplierID).orElse(null);
        if (Objects.isNull(supplier)){
            throw new ResourceNotFoundException("Not Found");
        }
        supplierRepository.delete(supplier);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> edit(Long supplierID,SupplierDTO dto){
        Supplier supplier = supplierRepository.findById(supplierID).orElse(null);
        if (Objects.isNull(supplier)){
            throw new ResourceNotFoundException("Not Found");
        }
        supplier.setName(dto.getName());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setCode(dto.getCode());
        supplier.setAddress(dto.getAddress());
        supplierRepository.saveAndFlush(supplier);
        return ResponseEntity.ok(true);
    }
}
