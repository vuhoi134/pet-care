package com.poly.petcare.domain.services;

import com.poly.petcare.app.responses.CartProductResponse;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.mapper.CartProductMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.poly.petcare.app.dtos.CartProductDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartProductServices extends BaseServices {

    @Autowired private CartProductMapper cartProductMapper;

    @Autowired private ProductMapper productMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public BaseApiResult addToCart(@RequestBody CartProductDTO dto) {
//        BaseApiResult result = new BaseApiResult();
//        ProductStore productStore = productStoreRepository.getOne(dto.getProductId());
//        if (dto.getGuid() != null && dto.getProductId() > 0) {
//            Cart cartEntity = cartRepository.findFirstCartByGuid(dto.getGuid());
//            Product productEntity = productRepository.getOne(dto.getProductId());
//
//            if (dto.getAmount() > productStore.getQuantityStore()){
//                throw new ResourceNotFoundException("Quantity must not be exceeded");
//            }
//
//            if (cartEntity != null && productEntity != null) {
//                CartProduct cartProductEntity = cartProductRepository.findFirstCartProductByCartIdAndProductId(cartEntity.getId(), productEntity.getId());
//                if (cartProductEntity != null) {
//                    System.out.println(productStore.getQuantityStore()-dto.getAmount());
//                    cartProductEntity.setAmount(cartProductEntity.getAmount() + dto.getAmount());
//                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
//                    cartProductRepository.saveAndFlush(cartProductEntity);
//                    productStoreRepository.saveAndFlush(productStore);
//                } else {
//                    CartProduct cartProduct = new CartProduct();
//                    cartProduct.setAmount(dto.getAmount());
//                    cartProduct.setCart(cartEntity);
//                    cartProductRepository.save(cartProduct);
//                }
//                result.setMessage("Add to cart successfully!");
//                result.setSuccess(true);
//                return result;
//            }
//        }
//        result.setMessage("Fail!");
//        result.setSuccess(false);
//        return result;
//    }
    public BaseApiResult addToCart(CartProductDTO dto) {
        BaseApiResult result = new BaseApiResult();
        ProductStore productStore = productStoreRepository.getOne(dto.getProductId());
        if (dto.getGuid() != null && dto.getProductId() > 0) { // khách hàng vãng lai
            Cart cartEntity = cartRepository.findByGuid(dto.getGuid());
            Product productEntity = productRepository.getOne(dto.getProductId());
            if (dto.getAmount() > productStore.getQuantityStore()){
                throw new ResourceNotFoundException("Quantity must not be exceeded");
            }

            if (cartEntity != null && productEntity != null) {
                CartProduct cartProductEntity = cartProductRepository.findFirstCartProductByCartIdAndProductId(cartEntity.getId(), productEntity.getId());
                if (cartProductEntity != null) {
                    cartProductEntity.setAmount(cartProductEntity.getAmount() + dto.getAmount());
                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
                    cartProductRepository.saveAndFlush(cartProductEntity);
                    productStoreRepository.saveAndFlush(productStore);
                } else {
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setAmount(dto.getAmount());
                    cartProduct.setCart(cartEntity);
                    cartProduct.setProduct(productEntity);
                    cartProductRepository.save(cartProduct);
                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
                    productStoreRepository.saveAndFlush(productStore);
                }
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }
            if(cartEntity == null && productEntity != null){
                Cart cartE=new Cart();
                cartE.setGuid(dto.getGuid());
                if(dto.getUserId()!=null){
                    cartE.setUser(userRepository.getOne(dto.getUserId()));
                }
                Cart cartE2=cartRepository.save(cartE);
                CartProduct cartProduct = new CartProduct();
                cartProduct.setAmount(dto.getAmount());
                cartProduct.setCart(cartE2);
                cartProduct.setProduct(productEntity);
                cartProductRepository.save(cartProduct);
                productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
                productStoreRepository.saveAndFlush(productStore);
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    public ResponseEntity<?> getCart(String guid,long userId){
        DataApiResult result = new DataApiResult();
        if(guid!=null && userId >0){
            Cart cartEntity=cartRepository.findByGuidAndUser(guid,userRepository.getOne(userId));
            List<CartProduct> cartProductList=cartProductRepository.findAllByCart(cartEntity);
            List<CartProductResponse> list=new ArrayList<>();
            for (CartProduct item:cartProductList) {
                CartProductResponse c=cartProductMapper.convertToDTO(item);
                c.setProductResponse(productMapper.convertToDTO(item.getProduct()));
                list.add(c);
            }
            result.setSuccess(true);
            result.setMessage("List cart product");
            result.setData(list);
            result.setTotalItem(Long.valueOf(list.size()));
            return ResponseEntity.ok(result);
        }
        if(guid!=null && userId ==0){
            Cart cartEntity=cartRepository.findByGuid(guid);
            List<CartProduct> cartProductList=cartProductRepository.findAllByCart(cartEntity);
            List<CartProductResponse> list=new ArrayList<>();
            for (CartProduct item:cartProductList) {
                CartProductResponse c=cartProductMapper.convertToDTO(item);
                c.setProductResponse(productMapper.convertToDTO(item.getProduct()));
                list.add(c);
            }
            result.setSuccess(true);
            result.setMessage("List cart product");
            result.setData(list);
            result.setTotalItem(Long.valueOf(list.size()));
            return ResponseEntity.ok(result);
        }
        result.setMessage("False");
        result.setSuccess(false);
        return ResponseEntity.ok(result);
    }

//    public BaseApiResult deleteCartProduct(@PathVariable long cartProductId) {
//        BaseApiResult result = new BaseApiResult();
//        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);
//        if (Objects.isNull(cartProduct)) {
//            throw new ResourceNotFoundException("Not found");
//        }
//        cartProductRepository.delete(cartProduct);
//        result.setMessage("Delete success");
//        result.setSuccess(true);
//        return result;
//    }
public BaseApiResult deleteCartProduct(long[] cartProductId) {
    BaseApiResult result = new BaseApiResult();
    for (long id:cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(id).orElse(null);
        if (Objects.isNull(cartProduct)) {
            throw new ResourceNotFoundException("Not found");
        }
        cartProductRepository.delete(cartProduct);
    }
    result.setMessage("Delete success");
    result.setSuccess(true);
    return result;
}
}
