package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.dtos.response.ProductResponse;
import com.poly.petcare.app.responses.ProductStoreResponse;
import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.entites.ProductStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class ProductStoreMapper {

    public ProductStoreResponse convertToDTO(ProductStore productStore){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        ProductStoreResponse productStoreResponse =modelMapper.map(productStore, ProductStoreResponse.class);
        return productStoreResponse;
    }
}
