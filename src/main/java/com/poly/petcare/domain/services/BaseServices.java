package com.poly.petcare.domain.services;

import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.mapper.ModelMapper;
import com.poly.petcare.domain.repository.*;
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
    protected SupplierRepository supplierRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected OrderDetailRepository orderDetailRepository;
    @Autowired
    protected TransactionRepository transactionRepository;
    @Autowired
    protected MailSerivce mailSerivce;
    @Autowired
    protected WearHouseRepository wearHouseRepository;
    @Autowired
    protected InputDetailRepository inputDetailRepository;
    @Autowired
    protected OrderTransactionRepository orderTransactionRepository;
    @Autowired
    protected ProductAttributeValueRepository productAttributeValueRepository;
    @Autowired
    protected ProductWarehouseRepository productWarehouseRepository;
//    @Autowired
//    protected OutputRepository outputRepository;
    @Autowired
    protected OutputDetailRepository outputDetailRepository;
}
