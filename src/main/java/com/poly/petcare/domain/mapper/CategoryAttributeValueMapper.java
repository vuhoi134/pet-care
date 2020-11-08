package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.CategoryAttributeValueResponses;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryAttributeValueMapper {

    public CategoryAttributeValueResponses convertToDTO(CategoryAttributeValue categoryAttributeValue){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        CategoryAttributeValueResponses categoryAttributeValueResponses =modelMapper.map(categoryAttributeValue, CategoryAttributeValueResponses.class);
        return categoryAttributeValueResponses;
    }
}
