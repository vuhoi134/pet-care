package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.ProductResponse;
import com.poly.petcare.domain.entites.Product;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class ProductMapper {

    public ProductResponse convertToDTO(Product product){
        ModelMapper modelMapper= new ModelMapper();
        ProductResponse productResponse =modelMapper.map(product, ProductResponse.class);
        return productResponse;
    }
}
