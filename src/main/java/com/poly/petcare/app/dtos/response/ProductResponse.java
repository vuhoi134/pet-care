package com.poly.petcare.app.dtos.response;

import com.poly.petcare.domain.entites.Brand;
import com.poly.petcare.domain.entites.CategoryAttributeValue;
import com.poly.petcare.domain.entites.ProductImage;
import com.poly.petcare.domain.entites.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {

    private Long id;

    private String code;

    private String name;

    private String mainImage;

    private String descriptionLong;

    private String descriptionShort;

    private String brandName;

    private String unitValue;

    private String categoryId;

    private String categoryName;

    private List<CategoryAttributeValueResponses> categoryAttributeValueResponses;

    private List<ProductImage> productImageList;

}
