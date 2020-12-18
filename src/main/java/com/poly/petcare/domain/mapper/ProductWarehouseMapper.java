package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.app.responses.ProductWarehouseResponse;
import com.poly.petcare.domain.entites.ProductStore;
import com.poly.petcare.domain.entites.ProductWarehouse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductWarehouseMapper {

    public ProductWarehouseResponse convertToDTO(ProductWarehouse productWarehouse){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        ProductWarehouseResponse productWarehouseResponse =modelMapper.map(productWarehouse, ProductWarehouseResponse.class);
        return productWarehouseResponse;
    }
}
