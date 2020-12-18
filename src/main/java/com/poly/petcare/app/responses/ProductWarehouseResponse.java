package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWarehouseResponse {
    private Long id;
    private Long quantityWarehouse;
    private Long expiryDate;
    private String codeTag;
    private ProductResponse product;
}
