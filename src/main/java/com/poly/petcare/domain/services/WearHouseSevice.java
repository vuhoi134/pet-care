package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.SupplierDTO;
import com.poly.petcare.app.dtos.WearHouseDTO;
import com.poly.petcare.app.responses.WarehouseResponse;
import com.poly.petcare.domain.entites.Supplier;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.entites.Warehouse;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WearHouseSevice extends BaseServices {
    @Autowired
    private WarehouseMapper warehouseMapper;

    public ResponseEntity<?> edit(Long wearhouseID, WearHouseDTO dto) {
        Warehouse warehouse = warehouseRepository.findById(wearhouseID).orElse(null);
        if (Objects.isNull(warehouse)) {
            throw new ResourceNotFoundException("Not Found");
        }
        warehouse.setAddress(dto.getAddress());
        warehouse.setCode(dto.getCode());
        warehouseRepository.saveAndFlush(warehouse);
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> create(WearHouseDTO dto) {
        User user = userRepository.getOne(dto.getUserID());
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(dto.getCode());
        warehouse.setAddress(dto.getAddress());
        warehouse.setUser(user);
        warehouseRepository.save(warehouse);
        return ResponseEntity.ok(true);
    }

    public List<WarehouseResponse> listWarehouse(){
        List<Warehouse> listWarehouses=warehouseRepository.findAll();
        List<WarehouseResponse> responseList=new ArrayList<>();
        for (Warehouse item:listWarehouses) {
            WarehouseResponse warehouseResponse=warehouseMapper.convertToDTO(item);
            responseList.add(warehouseResponse);
        }
        return responseList;
    }


}