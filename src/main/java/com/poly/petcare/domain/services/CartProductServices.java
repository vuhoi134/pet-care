package com.poly.petcare.domain.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@Service
public class CartProductServices extends BaseServices {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseApiResult addToCart(@RequestBody CartProductDTO dto) {
        BaseApiResult result = new BaseApiResult();
        ProductStore productStore = productStoreRepository.getOne(dto.getProductId());
        if (dto.getGuid() != null && dto.getProductId() > 0) {
            Cart cartEntity = cartRepository.findFirstCartByGuid(dto.getGuid());
            Product productEntity = productRepository.getOne(dto.getProductId());

            if (dto.getAmount() > productStore.getQuantityStore()){
                throw new ResourceNotFoundException("Quantity must not be exceeded");
            }

            if (cartEntity != null && productEntity != null) {
                CartProduct cartProductEntity = cartProductRepository.findFirstCartProductByCartIdAndProductId(cartEntity.getId(), productEntity.getId());
                if (cartProductEntity != null) {
                    cartProductEntity.setAmount(cartProductEntity.getAmount() + dto.getAmount());
                    cartProductRepository.saveAndFlush(cartProductEntity);
                } else {
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setAmount(dto.getAmount());
                    cartProduct.setCart(cartEntity);
                    cartProductRepository.save(cartProduct);
                }
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    public BaseApiResult deleteCartProduct(@PathVariable long cartProductId) {
        BaseApiResult result = new BaseApiResult();
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);
        if (Objects.isNull(cartProduct)) {
            throw new ResourceNotFoundException("Not found");
        }
        cartProductRepository.delete(cartProduct);
        result.setMessage("Delete success");
        result.setSuccess(true);
        return result;
    }


}
