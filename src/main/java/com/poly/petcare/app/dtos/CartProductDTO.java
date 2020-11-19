package com.poly.petcare.app.dtos;

import com.poly.petcare.domain.entites.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartProductDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private String codeTag;
    private Product product;
    private Integer amount;
    private BigDecimal price;
    private Integer quantity;
    private String guid;
    private List<CategoryAttributeValueDTO> value;
}

