package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.dtos.WearHouseDTO;
import com.poly.petcare.domain.services.SupplierService;
import com.poly.petcare.domain.services.WearHouseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/wearhouse")
public class WearHouseControllerApi {

    @Autowired
    WearHouseSevice wearHouseSevice;

    @PutMapping("edit/{wearhouseID}")
    public ResponseEntity<?> edit(@Valid @RequestBody WearHouseDTO wearHouseDTO, @PathVariable Long wearhouseID) {
        return wearHouseSevice.edit(wearhouseID, wearHouseDTO);
    }
    @PostMapping(value = "create")
    public ResponseEntity<?> create(@Valid @RequestBody WearHouseDTO wearHouseDTO){
        return wearHouseSevice.create(wearHouseDTO);
    }

}