package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.BrandDTO;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServices extends BaseServices {
    public ResponseEntity<?> create(BrandDTO dto) {
        Brand brand = Brand.builder()
                .name(dto.getName())
                .build();
        brandRepository.save(brand);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> listBrand(){
        DataApiResult result = new DataApiResult();
        List<Brand> list=brandRepository.findAll();
        result.setMessage("List Brand");
        result.setSuccess(true);
        result.setTotalItem(Long.valueOf(list.size()));
        result.setData(list);
        return ResponseEntity.ok(result);
    }
}
