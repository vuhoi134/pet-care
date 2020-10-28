package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.RoleIdConstant;
import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.CartProductDTO;
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
    public ResponseEntity<?> create(CartProductDTO dto) {

//        Product product = productRepository.getOne(dto.getProductId());
//        CartProduct cartProduct = new CartProduct();
//        if (product.getQuantity() <= 0 && product.getQuantity() < dto.getAmount()) {
//            throw new ResourceNotFoundException("Quantity of goods is not enough");
//        }
//        cartProduct.setProduct(product);
//        cartProduct.setAmount(dto.getAmount());
//        product.setQuantity(product.getQuantity() - dto.getAmount());
//        if (product.getQuantity() == 0) {
//            product.setStates(StatesConstant.SOLD);
//        }
//        cartProductRepository.save(cartProduct);
//        productRepository.save(product);
        return ResponseEntity.ok(true);
    }


}
