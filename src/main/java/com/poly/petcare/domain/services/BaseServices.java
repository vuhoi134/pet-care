package com.poly.petcare.domain.services;

import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.mapper.ModelMapper;
import com.poly.petcare.domain.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BaseServices {
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected CategoryAttributeRepository categoryAttributeRepository;
    @Autowired
    protected CategoryAttributeValueRepository categoryAttributeValueRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserRoleRepository userRoleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected ProfileRepository profileRepository;
    @Autowired
    protected ProductImageRepository productImageRepository;
    @Autowired
    protected BrandRepository brandRepository;
    @Autowired
    protected CartProductRepository cartProductRepository;
    @Autowired
    protected WarehouseRepository warehouseRepository;
    @Autowired
    protected ProductStoreRepository productStoreRepository;
    @Autowired
    protected CartRepository cartRepository;
    public CartProduct findFirstCartProductByCartIdAndProductId(int cartId, int productId) {
        try {
            return cartProductRepository.findFirstCartProductByCartIdAndProductId(cartId,productId);
        }catch (Exception e) {

        }
        return null;
    }
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected OrderDetailRepository orderDetailRepository;
    @Autowired
    protected TransactionRepository transactionRepository;
}
