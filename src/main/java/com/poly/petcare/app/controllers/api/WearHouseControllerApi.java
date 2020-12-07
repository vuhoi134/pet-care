package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.dtos.WearHouseDTO;
import com.poly.petcare.app.responses.WarehouseResponse;
import com.poly.petcare.domain.services.SupplierService;
import com.poly.petcare.domain.services.WearHouseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/warehouse")
public class WearHouseControllerApi {

    @Autowired
    WearHouseSevice wearHouseSevice;

    @PutMapping("edit/{warehouseID}")
    public ResponseEntity<?> edit(@Valid @RequestBody WearHouseDTO wearHouseDTO, @PathVariable Long wearhouseID) {
        return wearHouseSevice.edit(wearhouseID, wearHouseDTO);
    }
    @PostMapping(value = "create")
    public ResponseEntity<?> create(@Valid @RequestBody WearHouseDTO wearHouseDTO){
        return wearHouseSevice.create(wearHouseDTO);
    }

    @GetMapping(value = "listWarehouse")
    public List<WarehouseResponse> listWarehouse(){
        return wearHouseSevice.listWarehouse();
    }

}