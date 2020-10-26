package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.CountryDTO;
import com.poly.petcare.domain.entites.Country;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountryServices extends BaseServices {
    public ResponseEntity<?> create(CountryDTO dto) {
        Country country = Country.builder()
                .name(dto.getName())
                .build();
        countryRepository.save(country);
        System.out.println("OK");
        return ResponseEntity.ok(true);

    }
}
