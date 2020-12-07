package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.ProfileDTO;
import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.responses.SupplierResponse;
import com.poly.petcare.domain.entites.Supplier;
import com.poly.petcare.domain.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/supplier")
public class SupplierControllerApi {
    @Autowired
    SupplierService supplierService;
    @PutMapping("edit/{supplierID}")
    public ResponseEntity<?> edit(@Valid @RequestBody SupplierDTO supplierDTO, @PathVariable Long supplierID) {
        return supplierService.edit(supplierID,supplierDTO);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@Valid @RequestBody SupplierDTO supplierDTO){
        return supplierService.create(supplierDTO);
    }
    @DeleteMapping(value = "/delete/{supplierID}")
    public ResponseEntity<?> delete(@PathVariable Long supplierID){
        return supplierService.delete(supplierID);
    }

    @GetMapping(value = "listSupplier")
    public List<SupplierResponse> listSupplier(){
        return supplierService.listSupplier();
    }
}
