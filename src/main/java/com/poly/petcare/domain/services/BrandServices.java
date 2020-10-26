package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.domain.entites.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BrandServices extends BaseServices {
    public ResponseEntity<?> create(BrandDTO dto) {
        Brand brand = Brand.builder()
                .name(dto.getName())
                .build();
        brandRepository.save(brand);
        return ResponseEntity.ok(true);

    }
}
