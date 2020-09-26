package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.CategoryResponses;
import com.poly.petcare.app.responses.ProductResponses;
import com.poly.petcare.domain.entites.Category;
import com.poly.petcare.domain.entites.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "productName", source = "name")

    })
    ProductResponses productResponses(Product product);

    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),

    })
    CategoryResponses categoryResponses(Category category);

}
