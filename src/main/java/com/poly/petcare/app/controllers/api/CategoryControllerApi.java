package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.dtos.ProductDTO;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/category")
public class CategoryControllerApi {
    @Autowired
    private CategoryServices categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCategory(
            @RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @GetMapping(value = "searchByCategory/{level}")
    public ResponseEntity<?> infoCategory(@PathVariable Integer level) {
        return categoryService.searchByCategory(level);
    }

    @GetMapping(value = "/listCategory")
    public ResponseEntity<?> listCategory(
    ) {
        return categoryService.listCategory();
    }
}
