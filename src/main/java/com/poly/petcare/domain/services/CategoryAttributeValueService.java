package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryAttributeValueDTO;
import com.poly.petcare.app.dtos.response.CategoryAttributeValueResponses;
import com.poly.petcare.app.dtos.response.ProductResponse;
import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.domain.entites.CategoryAttribute;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.mapper.CategoryAttributeValueMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.mapper.ProductStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryAttributeValueService extends BaseServices {

    private ProductMapper productMapper;
    private ProductStoreMapper productStoreMapper;
    private CategoryAttributeValueMapper categoryAttributeValueMapper;

    @Autowired
    public CategoryAttributeValueService(ProductMapper productMapper, ProductStoreMapper productStoreMapper,
                            CategoryAttributeValueMapper categoryAttributeValueMapper
    ) {
        this.productMapper = productMapper;
        this.productStoreMapper = productStoreMapper;
        this.productRepository = productRepository;
        this.categoryAttributeValueMapper = categoryAttributeValueMapper;
    }

    public ResponseEntity<?> create(CategoryAttributeValueDTO dto) {

        CategoryAttribute attribute =
                categoryAttributeRepository.findById(dto.getCategoryAttributeID()).orElse(null);
        if (Objects.isNull(attribute)) {
            throw new ResourceNotFoundException("Not found attribute:" + " " + dto.getCategoryAttributeID());
        }
        CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
        categoryAttributeValue.setValue(dto.getValue());
        categoryAttributeValue.setCategoryAttribute(attribute);
        categoryAttributeValueRepository.save(categoryAttributeValue);
        return ResponseEntity.ok("Success!");
    }

    public ResponseEntity<?> edit(Long categoryAttributeValueID, CategoryAttributeValueDTO dto) {
        CategoryAttribute attribute =
                categoryAttributeRepository.findById(dto.getCategoryAttributeID()).orElse(null);
        if (Objects.isNull(attribute)) {
            throw new ResourceNotFoundException("Not found attribute:" + " " + categoryAttributeValueID);
        }
        CategoryAttributeValue value = categoryAttributeValueRepository.findById(categoryAttributeValueID).orElse(null);
        if (Objects.isNull(value)) {
            throw new ResourceNotFoundException("Not found value :" + " " + categoryAttributeValueID);
        }
        value.setValue(dto.getValue());
        value.setCategoryAttribute(attribute);
        categoryAttributeValueRepository.saveAndFlush(value);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> delete(Long categoryAttributeValueID) {
        CategoryAttributeValue value = categoryAttributeValueRepository.findById(categoryAttributeValueID).orElse(null);
        if (Objects.isNull(value)) {
            throw new ResourceNotFoundException("Not found value");
        }
        categoryAttributeValueRepository.deleteById(categoryAttributeValueID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> getCategoryAttributeValue(Long categoryAttributeValueID) {
        CategoryAttributeValue attributeValue =
                categoryAttributeValueRepository.findById(categoryAttributeValueID).orElse(null);
        if (Objects.isNull(attributeValue)) {
            throw new ResourceNotFoundException("Empty");
        }
        List<ProductResponse> list=new ArrayList<>();
        for (Product p:attributeValue.getProduct()) {
            ProductResponse productResponse=new ProductResponse();
            productResponse=productMapper.convertToDTO(p);
            List<CategoryAttributeValueResponses> listC=new ArrayList<>();
            for (CategoryAttributeValue c:p.getCategoryAttributeValues()) {
                CategoryAttributeValueResponses categoryAttributeValueResponses=categoryAttributeValueMapper.convertToDTO(c);
                listC.add(categoryAttributeValueResponses);
            }
            productResponse.setCategoryAttributeValueResponses(listC);
            list.add(productResponse);
        }
        return ResponseEntity.ok(list);
    }

}
