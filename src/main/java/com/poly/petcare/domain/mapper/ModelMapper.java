package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.*;
import com.poly.petcare.domain.entites.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ModelMapper {
//    @Mappings({
//            @Mapping(target = "productName", source = "name"),
//            @Mapping(target = "category", source = "category.name"),
//            @Mapping(target = "brand", source = "brand.name"),
//            @Mapping(target = "values", source = "categoryAttributeValues"),
//    })
//    ProductResponses productResponses(Product product);

    @Mappings({
            @Mapping(target = "parentId", source = "parentId.id"),
            @Mapping(target = "childen", source = "children"),
//            @Mapping(target = "productDTOList", source = "productList"),
            @Mapping(target = "listAttribute", source = "listAttribute")
    })
    CategoryResponses categoryToResponse(Category category);

//    @Mappings({
//            @Mapping(target = "categoryAttributeID", source = "categoryAttribute.id"),
//            @Mapping(target = "attribute", source = "categoryAttribute.name")
//    })
//    CategoryAttributeValueResponses valueToResponse(CategoryAttributeValue categoryAttributeValue);

    @Mappings({
            @Mapping(target = "value", source = "listValue"),
            @Mapping(target = "categoryID", source = "category.id")
    })
    CategoryAttributeResponses attributeToResponse(CategoryAttribute categoryAttribute);



    @Mappings({
    })
    ProfileResponses profileResponses(Profile profile);

    @Mappings({
            @Mapping(target = "fullName", source = "profile.fullName"),
    })
    UserResponses userResponses(User user);

}
