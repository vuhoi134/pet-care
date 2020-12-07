package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputDTO {
    private Long userId;
    private Long warehouseId;
    private String transporter;
    private String phoneTransporter;
    private List<InputDetailDTO> inputDetailDTOS;
}
