package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.responses.CategoryResponses;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.services.BaseServices;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServices extends BaseServices {


    public ResponseEntity<?> create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setLevel(categoryDTO.getLevel());
        category.setStates(categoryDTO.getStates());
        if (Objects.nonNull(categoryDTO.getParentId())) {
            Category parent = categoryRepository.findById(categoryDTO.getParentId()).orElse(null);
            if (parent != null) {
                category.setParentId(parent);
            }
        }
        categoryRepository.save(category);
        return ResponseEntity.ok("Success!");
    }


    public ResponseEntity<?> infoCategory(Long categoryID) {
        Category category = categoryRepository.findById(categoryID).orElse(null);
        if (Objects.isNull(category)) {
            throw new ResourceNotFoundException("Not found categoryID:" + categoryID);
        }
        CategoryResponses responses = modelMapper.categoryResponses(category);
        return ResponseEntity.ok(responses);

    }


}