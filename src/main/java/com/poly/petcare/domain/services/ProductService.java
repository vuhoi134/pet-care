package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductService extends BaseServices {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public ResponseEntity createProduct(ProductDTO dto) {
        Category category = categoryRepository.getOne(dto.getCategoryID());

        List<CategoryAttributeValue> categoryAttributeValues = new ArrayList<>();
        for (Long id : dto.getCategoryAttributeValueID()) {
            CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
            categoryAttributeValue.setId(id);
            categoryAttributeValues.add(categoryAttributeValue);
        }
        Product product = Product.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .descriptionShort(dto.getDescription_Short())
                .price(dto.getPrice())
                .descriptionLong(dto.getDescription_Long())
                .category(category)
                .image(dto.getMainImage())
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
        Category category = categoryRepository.findById(productDTO.getCategoryID()).orElse(null);
        if (Objects.isNull(category)) {
            throw new ResourceNotFoundException("Not found categoryID:" + productDTO.getCategoryID());
        }
        categoryRepository.getOne(productDTO.getCategoryID());
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
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setImage(productDTO.getMainImage());
        product.setDescriptionShort(productDTO.getDescription_Short());
        product.setDescriptionLong(productDTO.getDescription_Long());
        product.setCategoryAttributeValues(categoryAttributeValues);
        productRepository.saveAndFlush(product);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> search(String name) {
        List<Product> list = productRepository.search(name);
        if (list.size() == 0) {
            throw new ResourceNotFoundException("Not found:");
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
        Pageable pageable = PageRequest.of(curunpage, totalpage);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponses> responsesList = new ArrayList<>();
        for (Product product : productPage) {
            ProductResponses responses = modelMapper.productResponses(product);
            responsesList.add(responses);
        }
        return ResponseEntity.ok(responsesList);
    }


}
