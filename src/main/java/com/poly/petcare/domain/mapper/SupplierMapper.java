package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.SupplierResponse;
import com.poly.petcare.app.responses.WarehouseResponse;
import com.poly.petcare.domain.entites.Supplier;
import com.poly.petcare.domain.entites.Warehouse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponse convertToDTO(Supplier supplier){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        SupplierResponse supplierResponse =modelMapper.map(supplier, SupplierResponse.class);
        return supplierResponse;
    }
}
