package com.poly.petcare.app.responses;

import com.poly.petcare.domain.entites.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponse {
    private List<CartProduct> cartProducts;
}
