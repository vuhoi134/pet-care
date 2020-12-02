package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.services.CartProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/cart-product")
public class CartProductControllerApi {
    @Autowired
    private CartProductServices cartProductServices;

    @PostMapping("/create")
    public BaseApiResult create(@Valid @RequestBody CartProductDTO dto) {
        return cartProductServices.addToCart(dto);
    }

    @GetMapping("/listCartProduct")
    public ResponseEntity<?> list(@RequestParam(name = "guid") String guid,
                                  @RequestParam(name = "userId") Long userId) {
        return cartProductServices.getCart(guid,userId);
    }

    @DeleteMapping("delete")
    public BaseApiResult delete(@RequestBody long[] cartProductId) {
        return cartProductServices.deleteCartProduct(cartProductId);
    }
    @PutMapping("update")
    public BaseApiResult update(@RequestParam(name = "cartProductId") Long cartProductId,
                                @RequestParam(name = "amount") Integer amount) {
        return cartProductServices.updateCart(cartProductId,amount);
    }
}
