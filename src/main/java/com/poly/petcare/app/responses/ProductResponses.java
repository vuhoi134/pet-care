package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponses {

    private Long id;
    private String category;
    private String brand;
    private String country;
    private String mainImage;
    private String productName;
    private Double price;
    private Boolean states;
    private Integer quantity;
    private Float discounts;
    private String descriptionShort;
    private String descriptionLong;
    private List<CategoryAttributeValueResponses> values;

}
