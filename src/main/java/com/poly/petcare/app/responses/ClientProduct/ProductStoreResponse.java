package com.poly.petcare.app.responses.ClientProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStoreResponse {
    private Integer quantity;
    private Long expiryDate;
    private String codeTag;
}
