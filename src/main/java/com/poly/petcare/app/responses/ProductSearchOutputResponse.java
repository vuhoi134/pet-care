package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSearchOutputResponse {
    private Long id;

    private String name;

    private Long quantity;

    private String codeTag;

    private String attributeValue;

    private String unit;

    private BigDecimal price;
}
