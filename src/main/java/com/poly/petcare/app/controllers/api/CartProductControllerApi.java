package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.services.CartProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/cart-product")
public class CartProductControllerApi {
    @Autowired
    private CartProductServices cartProductServices;

    @PostMapping("/create")
    public BaseApiResult create(@Valid @RequestBody CartProductDTO dto) {
    return cartProductServices.addToCart(dto);
    }
    @DeleteMapping("delete/{cartProductId}")
    public BaseApiResult delete(@PathVariable long cartProductId){
        return cartProductServices.deleteCartProduct(cartProductId);
    }
}
