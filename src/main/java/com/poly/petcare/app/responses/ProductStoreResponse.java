package com.poly.petcare.app.responses;

import com.poly.petcare.app.dtos.response.ProductResponse;
import com.poly.petcare.domain.entites.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStoreResponse {
    private Integer quantity;
    private Long expiryDate;
    private String tag;
    private ProductResponse product;
}
