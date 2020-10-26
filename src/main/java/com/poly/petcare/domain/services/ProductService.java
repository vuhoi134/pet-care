package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.dtos.ProductImageDTO;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductService extends BaseServices {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity createProduct(ProductDTO dto) {
        Category category = categoryRepository.getOne(dto.getCategoryID());
        Brand brand = brandRepository.getOne(dto.getBrandID());
        Country country = countryRepository.getOne(dto.getCountryID());
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : dto.getCategoryAttributeValueID()) {
            CategoryAttributeValue Value = new CategoryAttributeValue();
            Value.setId(id);
            categoryAttributeValues.add(Value);
        }
        Product product = Product.builder()
                .name(dto.getName())
                .descriptionShort(dto.getDescription_Short())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .states(StatesConstant.NOT_SOLD_YET)
                .discounts(dto.getDiscounts())
                .descriptionLong(dto.getDescription_Long())
                .category(category)
                .brand(brand)
                .country(country)
                .mainImage(dto.getMainImage())
                .categoryAttributeValues(categoryAttributeValues)
                .build();

        productRepository.save(product);
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> detail(Long productID) {
        Product product = productRepository.findById(productID).orElse(null);
        if (Objects.isNull(product)) {
            throw new ResourceNotFoundException("Not found product :" + productID);
        }
        ProductResponses responses = modelMapper.productResponses(product);
        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<?> listProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponses> responsesList = new ArrayList<>();
        for (Product product : productList) {
            ProductResponses responses = modelMapper.productResponses(product);
            responsesList.add(responses);
        }
        return ResponseEntity.ok(responsesList);
    }


    public ResponseEntity<?> edit(Long productID, ProductDTO productDTO) {
        Category category = categoryRepository.getOne(productDTO.getCategoryID());
        Brand brand = brandRepository.getOne(productDTO.getBrandID());
        Country country = countryRepository.getOne(productDTO.getCountryID());
        Discount discount = discountRepository.getOne(productDTO.getDiscountID());
        Product product = productRepository.findById(productID).orElse(null);
        if (Objects.isNull(product)) {
            throw new ResourceNotFoundException("Not found productID:" + productID);
        }
        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : productDTO.getCategoryAttributeValueID()) {
            CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
            categoryAttributeValue.setId(id);
            categoryAttributeValues.add(categoryAttributeValue);
        }
        product.setCategory(category);
        product.setBrand(brand);
        product.setQuantity(productDTO.getQuantity());
        product.setCountry(country);
        product.setPrice(productDTO.getPrice());
        if (product.getDiscount().getId() == 1) {
            product.setDiscounts(product.getPrice() - product.getPrice() * 0.1);
        } else if (product.getDiscount().getId() == 2) {
            product.setDiscounts(product.getPrice() - product.getPrice() * 0.2);
        } else if (product.getDiscount().getId() == 3) {
            product.setDiscounts(product.getPrice() - product.getPrice() * 0.3);
        }
        product.setDiscount(discount);
        product.setStates(productDTO.getStates());
        product.setName(productDTO.getName());
        product.setMainImage(productDTO.getMainImage());
        product.setDescriptionShort(productDTO.getDescription_Short());
        product.setDescriptionLong(productDTO.getDescription_Long());
        product.setCategoryAttributeValues(categoryAttributeValues);
        productRepository.saveAndFlush(product);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> search(String name) {
        List<Product> list = productRepository.findByName(name);
        if (list.size() == 0) {
            throw new ResourceNotFoundException("List Empty");
        }
        List<ProductResponses> responsesList = new ArrayList<>();
        for (Product product : list) {
            ProductResponses responses = modelMapper.productResponses(product);
            responsesList.add(responses);
        }
        return ResponseEntity.ok(responsesList);
    }

    public ResponseEntity<?> page(Pageable pageable) {
        Page<Product> productList = productRepository.findAll(pageable);
        List<ProductResponses> responsesList = new ArrayList<>();
        for (Product product : productList) {
            ProductResponses responses = modelMapper.productResponses(product);
            responsesList.add(responses);
        }
        return ResponseEntity.ok(responsesList);
    }

    public ResponseEntity<?> pageProduct(Integer curunpage, Integer totalpage) {
        DataApiResult result = new DataApiResult();
        Pageable pageable = PageRequest.of(curunpage, totalpage);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponses> responsesList = new ArrayList<>();
        for (Product product : productPage) {
            ProductResponses responses = modelMapper.productResponses(product);
            responsesList.add(responses);
        }
        result.setSuccess(true);
        result.setMessage("Total Item :" + " " + productRepository.findAll().size());
        result.setData(responsesList);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> listImage(Long productId) {
        Product product = productRepository.getOne(productId);
        List<ProductImageDTO> list = new ArrayList<>();
        for (ProductImage productImage : product.getProductImageList()) {
            ProductImageDTO dto = new ProductImageDTO();
            dto.setId(productImage.getId());
            dto.setLink(productImage.getLink());
            list.add(dto);
        }
        return ResponseEntity.ok(list);
    }

}
