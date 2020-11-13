package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.domain.services.BrandServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/brand")
@CrossOrigin
public class BrandControllerApi {
    @Autowired
    BrandServices brandServices;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@Valid @RequestBody BrandDTO dto) {
        return brandServices.create(dto);
    }

    @GetMapping(value = "listBrand")
    public ResponseEntity<?> listBrand(){
        return brandServices.listBrand();
    }
}
