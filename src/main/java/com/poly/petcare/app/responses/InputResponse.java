package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputResponse {
    private String code;
    private String importDate;
    private String userName;
    private String warehouseAddress;
    private Integer status;
    private BigDecimal money;
    private List<InputDetailResponse> inputDetailResponses;
}
