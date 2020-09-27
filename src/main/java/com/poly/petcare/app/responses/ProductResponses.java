package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponses {

    private Long id;
    private String image;
    private String productName;
    private String categoryName;
    private Double price;
    private Integer quantity;
    private String descriptionShort;
    private String descriptionLong;

}
