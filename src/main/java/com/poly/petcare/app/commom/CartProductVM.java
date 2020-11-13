package com.poly.petcare.app.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartProductVM {
    private long id;
    private long productId;
    private String mainImage;
    private Integer amount;
    private String productName;
    private String price;
}
