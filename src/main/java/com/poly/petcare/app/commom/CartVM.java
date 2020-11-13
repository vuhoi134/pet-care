package com.poly.petcare.app.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartVM {
    private int productAmount;
    private List<CartProductVM> cartProductVMS;
    private String totalPrice;
}
