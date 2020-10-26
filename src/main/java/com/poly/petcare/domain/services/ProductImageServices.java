package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.ProductImageDTO;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.entites.ProductImage;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductImageServices extends BaseServices {
    public ResponseEntity<?> create(ProductImageDTO dto) {
        Product product = productRepository.getOne(dto.getProductId());

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .link(dto.getLink())
                .product(product)
                .build();
        productImageRepository.save(productImage);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> edit(ProductImageDTO dto, Long productImageID) {
        ProductImage productImage = productImageRepository.findById(productImageID).orElse(null);
        if (Objects.isNull(productImage)) {
            throw new ResourceNotFoundException("Not found productImageID" + " " + productImageID);
        }
        Product product = productRepository.getOne(dto.getProductId());
        if (Objects.isNull(product)) {
            throw new ResourceNotFoundException("Not found product" + " " + product);
        }
        productImage.setLink(dto.getLink());
        productImage.setProduct(product);
        productImageRepository.saveAndFlush(productImage);
        return ResponseEntity.ok(true);
    }

}
