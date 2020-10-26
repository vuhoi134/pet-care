package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.CountryDTO;
import com.poly.petcare.domain.services.CountryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/country")
public class CountryControllerApi {
    @Autowired
    CountryServices countryServices;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@Valid @RequestBody CountryDTO dto) {
        return countryServices.create(dto);
    }
}
