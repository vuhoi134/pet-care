package com.poly.petcare.app.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVM {
    private Long id;
    private String name;
    private Double discounts;
    private Boolean states;
    private Double price;
    private Integer quantity;
    private String descriptionShort;
    private String descriptionLong;
    private String mainImage;
    private List<ProductImageVM> productImageVMS;
}
