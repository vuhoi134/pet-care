package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.CategoryAttributeValueDTO;
import com.poly.petcare.domain.services.CategoryAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/category-attribute-value")
public class CategoryAttributeValueControllerApi {
    @Autowired
    private CategoryAttributeValueService categoryAttributeValueService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CategoryAttributeValueDTO dto) {
        return categoryAttributeValueService.create(dto);
    }

    @PutMapping(value = "{categoryAttributeValueID}")
    public ResponseEntity<?> delete(@PathVariable Long categoryAttributeValueID) {
        return categoryAttributeValueService.delete(categoryAttributeValueID);
    }

    @PutMapping(value = {"categoryAttributeValueID"})
    public ResponseEntity<?> edit(@PathVariable Long categoryAttributeValueID, CategoryAttributeValueDTO dto) {
        return categoryAttributeValueService.edit(categoryAttributeValueID, dto);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> find(@PathVariable Long id){
        return categoryAttributeValueService.getCategoryAttributeValue(id);
    }
}
