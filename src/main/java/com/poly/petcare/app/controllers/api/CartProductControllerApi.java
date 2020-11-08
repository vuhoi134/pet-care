package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.domain.services.CartProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/cart-product")
public class CartProductControllerApi {
    @Autowired
    private CartProductServices cartProductServices;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> create(@Valid @RequestBody CartProductDTO dto) {
//        return cartProductServices.create(dto);
//    }
}
