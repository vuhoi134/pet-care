package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputDetailDTO {
    private Long actualAmount;
    private Long theoreticalAmount;
    private BigDecimal importPrice;
    private Long supplierId;
    private Long productId;
    private Date expiryDate;
}
