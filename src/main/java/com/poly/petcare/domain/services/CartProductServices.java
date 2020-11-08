package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.RoleIdConstant;
import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.domain.entites.Cart;
import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartProductServices extends BaseServices {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> addToCart(CartProductDTO dto) {

//        Cart cartEntity = cartProductRepository.
        return null;
    }
}
