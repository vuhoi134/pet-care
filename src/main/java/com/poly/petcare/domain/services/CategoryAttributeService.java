package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryAttributeDTO;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.CategoryAttribute;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryAttributeService extends BaseServices {
    public ResponseEntity<?> create(CategoryAttributeDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryID()).orElse(null);
        if (Objects.isNull(category)) {
            throw new ResourceNotFoundException("Not found category :" + " " + category);
        }
        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setName(dto.getName());
        categoryAttribute.setCategory(category);
        categoryAttributeRepository.save(categoryAttribute);
        return ResponseEntity.ok("Success!");
    }

    public ResponseEntity<?> delete(Long categoryAttributeID) {
        CategoryAttribute attribute = categoryAttributeRepository.findById(categoryAttributeID).orElse(null);
        if (Objects.isNull(attribute)) {
            throw new ResourceNotFoundException("Not found category :" + " " + categoryAttributeID);
        }
        categoryAttributeRepository.deleteById(categoryAttributeID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> edit(Long categoryAttributeID, CategoryAttributeDTO dto) {
        CategoryAttribute attribute = categoryAttributeRepository.findById(categoryAttributeID).orElse(null);
        if (Objects.isNull(attribute)) {
            throw new ResourceNotFoundException("Not found category :" + " " + categoryAttributeID);
        }
        attribute.setName(dto.getName());
        return ResponseEntity.ok(true);
    }
}
