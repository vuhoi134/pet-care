package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputDetailResponse {
    private String codeTag;
    private String productName;
    private String image;
    private Long actualAmount;
    private Long theoreticalAmount;
    private String supplierName;
}
