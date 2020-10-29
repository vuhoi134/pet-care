package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.app.responses.CategoryResponses;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.services.BaseServices;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity<?> searchByCategory(Integer level) {
        List<Category> list = categoryRepository.findAllByLevel(level);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Empty");
        }
        List<CategoryResponses> dtoList = new ArrayList<>();
        for (Category category : list) {
            CategoryResponses responses = modelMapper.categoryToResponse(category);
            dtoList.add(responses);
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity listCategory() {
        List<Category> categoryList = categoryRepository.findAll();
//        List<CategoryResponses> dtoList = new ArrayList<>();
//        for (Category category : categoryList) {
//            CategoryResponses responses = modelMapper.categoryToResponse(category);
//            if (responses.getLevel().equals(0)) {
//                dtoList.add(responses);
//            }
//        }
        return ResponseEntity.ok(categoryList);
    }


}