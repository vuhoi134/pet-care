package com.poly.petcare.app.responses.ClientProduct;

import com.poly.petcare.app.responses.CategoryAttributeValueResponses;
import com.poly.petcare.domain.entites.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private Long id;

    private String code;

    private String name;

    private BigDecimal price;

    private String mainImage;

    private String descriptionLong;

    private String descriptionShort;

    private String brandName;

    private String unit;

    private String categoryId;

    private String categoryName;

    private List<CategoryAttributeValueResponses> categoryAttributeValueResponses;

    private List<ProductImage> productImageList;

    private List<ProductStoreResponse> productStoreResponses;

}
