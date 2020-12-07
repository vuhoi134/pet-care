package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.app.responses.WarehouseResponse;
import com.poly.petcare.domain.entites.ProductStore;
import com.poly.petcare.domain.entites.Warehouse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {
    public WarehouseResponse convertToDTO(Warehouse warehouse){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        WarehouseResponse warehouseResponse =modelMapper.map(warehouse, WarehouseResponse.class);
        return warehouseResponse;
    }
}
