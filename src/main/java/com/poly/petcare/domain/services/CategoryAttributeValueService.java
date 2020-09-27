package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryAttributeValueDTO;
import com.poly.petcare.domain.entites.CategoryAttribute;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryAttributeValueService extends BaseServices {
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


}
