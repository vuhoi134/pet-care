package com.poly.petcare.app.controllers;

import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/category")
public class CategoryController {
    @Autowired
    private CategoryServices categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCategory(
            @RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @GetMapping(value = "/{categoryID}")
    public ResponseEntity<?> infoCategory(@PathVariable Long categoryID) {
        return categoryService.infoCategory(categoryID);
    }



}
