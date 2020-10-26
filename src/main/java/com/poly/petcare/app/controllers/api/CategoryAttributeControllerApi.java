package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.CategoryAttributeDTO;
import com.poly.petcare.domain.services.BaseServices;
import com.poly.petcare.domain.services.CategoryAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/category-attribute")
public class CategoryAttributeControllerApi extends BaseServices {
    @Autowired
    private CategoryAttributeService categoryAttributeService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CategoryAttributeDTO dto) {
        return categoryAttributeService.create(dto);
    }

    @PutMapping(value = "{categoryAttributeID}")
    public ResponseEntity<?> edit(@Valid @RequestBody CategoryAttributeDTO dto, @PathVariable Long categoryAttributeID) {
        return categoryAttributeService.edit(categoryAttributeID, dto);
    }

    @DeleteMapping(value = "{categoryAttributeID}")
    public ResponseEntity<?> delete(@PathVariable Long categoryAttributeID) {
        return categoryAttributeService.delete(categoryAttributeID);
    }
}
