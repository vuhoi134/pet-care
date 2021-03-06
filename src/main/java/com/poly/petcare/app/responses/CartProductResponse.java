package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartProductResponse {
    private Long id;
    private Integer amount;
    private ProductResponse productResponse;
    private Long totalMoney;
    private Long quantityStore;
}
