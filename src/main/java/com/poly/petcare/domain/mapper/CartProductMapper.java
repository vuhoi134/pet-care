package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.CartProductResponse;
import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.entites.ProductStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartProductMapper {
    public CartProductResponse convertToDTO(CartProduct cartProduct){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        CartProductResponse cartProductResponse =modelMapper.map(cartProduct, CartProductResponse.class);
        return cartProductResponse;
    }
}
