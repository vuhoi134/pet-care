package com.poly.petcare.domain.services;

import com.poly.petcare.app.responses.CartProductResponse;
import com.poly.petcare.app.responses.CartResponse;
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
import org.springframework.data.domain.Sort;
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
    public BaseApiResult addToCart(CartProductDTO dto) {
        BaseApiResult result = new BaseApiResult();
        ProductStore productStore = productStoreRepository.getOne(dto.getProductId());
        if (dto.getGuid() != null && dto.getProductId() > 0 && dto.getUserId()==0) { // khách hàng vãng lai
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
                    cartProduct.setProduct(productEntity);
                    cartProduct.setCart(cartEntity);
                    cartProduct.setProduct(productEntity);
                    cartProductRepository.save(cartProduct);

//                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
//                    productStoreRepository.saveAndFlush(productStore);
                }
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }
            if(cartEntity == null && productEntity != null){
                Cart cartE=new Cart();
                cartE.setGuid(dto.getGuid());
                if(dto.getUserId()>0){
                    cartE.setUser(userRepository.getOne(dto.getUserId()));
                }
                Cart cartE2=cartRepository.save(cartE);
                CartProduct cartProduct = new CartProduct();
                cartProduct.setAmount(dto.getAmount());
                cartProduct.setCart(cartE2);
                cartProduct.setProduct(productEntity);
                cartProductRepository.save(cartProduct);

//                productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
//                productStoreRepository.saveAndFlush(productStore);

                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                result.setTotalItem(dto.getAmount().longValue());
                return result;
            }
        }
        if(dto.getUserId()>0 && dto.getProductId() > 0){ // khách hàng có tài khoản
            Product productEntity = productRepository.getOne(dto.getProductId());
            if (dto.getAmount() > productStore.getQuantityStore()){
                throw new ResourceNotFoundException("Quantity must not be exceeded");
            }

            Cart cartEntity = cartRepository.findByUser(userRepository.getOne(dto.getUserId()));

            if (cartEntity != null && productEntity != null) {
                CartProduct cartProductEntity = cartProductRepository.findFirstCartProductByCartIdAndProductId(cartEntity.getId(), productEntity.getId());
                if (cartProductEntity != null) {
                    System.out.println("Có vào");
                    cartProductEntity.setAmount(cartProductEntity.getAmount() + dto.getAmount());
                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
                    cartProductRepository.saveAndFlush(cartProductEntity);
                    productStoreRepository.saveAndFlush(productStore);
                } else {
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setAmount(dto.getAmount());
                    cartProduct.setProduct(productEntity);
                    cartProduct.setCart(cartEntity);
                    cartProduct.setProduct(productEntity);
                    cartProductRepository.save(cartProduct);

//                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
//                    productStoreRepository.saveAndFlush(productStore);
                }
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }else if (cartEntity == null && productEntity != null){
                Cart cart=new Cart();
                cart.setGuid(dto.getGuid());
                cart.setUser(userRepository.getOne(dto.getUserId()));
                Cart cart1=cartRepository.save(cart);

                CartProduct cartProductEntity = cartProductRepository.findFirstCartProductByCartIdAndProductId(cart1.getId(), productEntity.getId());
                if (cartProductEntity != null) {
                    cartProductEntity.setAmount(cartProductEntity.getAmount() + dto.getAmount());
                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
                    cartProductRepository.saveAndFlush(cartProductEntity);
                    productStoreRepository.saveAndFlush(productStore);
                } else {
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setAmount(dto.getAmount());
                    cartProduct.setProduct(productEntity);
                    cartProduct.setCart(cart1);
                    cartProduct.setProduct(productEntity);
                    cartProductRepository.save(cartProduct);

//                    productStore.setQuantityStore(productStore.getQuantityStore() - dto.getAmount());
//                    productStoreRepository.saveAndFlush(productStore);
                }
                result.setMessage("Add to cart successfully!");
                result.setSuccess(true);
                return result;
            }else{
                result.setMessage("Fail!");
                result.setSuccess(false);
                return result;
            }
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    public ResponseEntity<?> getCart(String guid,long userId){
        DataApiResult result = new DataApiResult();
        Sort sort = Sort.by("id").descending();
        int totalItem=0;
        if(guid!=null && userId >0){
            Long totalMoney=0L;
            List<Cart> cartEntity=cartRepository.findAllByUser(userRepository.getOne(userId),sort);
            List<CartResponse> cartResponseList=new ArrayList<>();
            for (Cart c:cartEntity) {
                CartResponse cartResponse=new CartResponse();
                cartResponse.setCartProducts(c.getListCartProducts());
                cartResponseList.add(cartResponse);
            }
            List<CartProductResponse> list=new ArrayList<>();
            for (CartResponse item:cartResponseList) {
                totalItem+=item.getCartProducts().size();
                for (CartProduct c:item.getCartProducts()) {
                    ProductStore productStore=productStoreRepository.findByProducts_Id(c.getProduct().getId());
                    CartProductResponse cartProductResponse = cartProductMapper.convertToDTO(c);
                    cartProductResponse.setProductResponse(productMapper.convertToDTO(c.getProduct()));
                    cartProductResponse.setTotalMoney(Long.valueOf(c.getAmount() * c.getProduct().getPrice().intValue()));
                    cartProductResponse.setQuantityStore(productStore.getQuantityStore());
                    totalMoney+=(Long.valueOf(c.getAmount() * c.getProduct().getPrice().intValue()));
                    list.add(cartProductResponse);
                }
            }
            result.setSuccess(true);
            result.setMessage("List cart product");
            result.setData(list);
            result.setTotalItem(Long.valueOf(totalItem));
            result.setTotalMoney(totalMoney);
            return ResponseEntity.ok(result);
//            Cart cartEntity=cartRepository.findByUser(userRepository.getOne(userId));
//            List<CartProduct> cartProductList=cartProductRepository.findAllByCart(cartEntity);
//            List<CartProductResponse> list=new ArrayList<>();
//            for (CartProduct item:cartProductList) {
//                CartProductResponse c=cartProductMapper.convertToDTO(item);
//                c.setProductResponse(productMapper.convertToDTO(item.getProduct()));
//                c.setTotalMoney(Long.valueOf(item.getAmount()*item.getProduct().getPrice().intValue()));
//                list.add(c);
//            }
//            result.setSuccess(true);
//            result.setMessage("List cart product");
//            result.setData(list);
//            result.setTotalItem(Long.valueOf(list.size()));
//            return ResponseEntity.ok(result);
        }
        if(guid!=null && userId ==0){
            Cart cartEntity=cartRepository.findByGuid(guid);
            List<CartProduct> cartProductList=cartProductRepository.findAllByCart(cartEntity);
            List<CartProductResponse> list=new ArrayList<>();
            Long totalMoney=0L;
            for (CartProduct item:cartProductList) {
                ProductStore productStore=productStoreRepository.findByProducts_Id(item.getProduct().getId());
                CartProductResponse c=cartProductMapper.convertToDTO(item);
                c.setProductResponse(productMapper.convertToDTO(item.getProduct()));
                c.setQuantityStore(productStore.getQuantityStore());
                totalMoney+=(Long.valueOf(item.getAmount()*item.getProduct().getPrice().intValue()));
                list.add(c);
            }
            result.setSuccess(true);
            result.setMessage("List cart product");
            result.setTotalMoney(totalMoney);
            result.setData(list);
            result.setTotalItem(Long.valueOf(list.size()));
            return ResponseEntity.ok(result);
        }
        result.setMessage("False");
        result.setSuccess(false);
        return ResponseEntity.ok(result);
    }

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
public BaseApiResult updateCart(Long cartProductId,Integer amount){
    BaseApiResult result = new BaseApiResult();
    CartProduct cartProduct=cartProductRepository.getOne(cartProductId);
    cartProduct.setAmount(amount);
    cartProductRepository.saveAndFlush(cartProduct);
    result.setMessage("Update cart-product success");
    result.setSuccess(true);
    return result;
}
}
