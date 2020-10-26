package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.dtos.ProductImageDTO;
import com.poly.petcare.domain.services.ProductImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/products-image")
public class ProductImageControllerApi {
    @Autowired
    private ProductImageServices productImageServices;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody ProductImageDTO productImageDTO) {
        return productImageServices.create(productImageDTO);
    }

    @PutMapping(value = "edit/{productImageID}")
    public ResponseEntity<?> edit(@Valid @RequestBody ProductImageDTO productImageDTO, @PathVariable Long productImageID) {
        return productImageServices.edit(productImageDTO, productImageID);
    }
}
