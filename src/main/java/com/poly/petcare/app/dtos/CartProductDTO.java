package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartProductDTO {
    private Long id;
    private Long productId;
    private Integer amount;
    private List<CategoryAttributeValueDTO> value;
}

