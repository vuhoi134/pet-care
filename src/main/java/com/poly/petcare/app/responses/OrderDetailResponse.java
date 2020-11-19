package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private String orderCode;
    private String orderUserName;
    private String orderEmail;
    private String orderPhoneNumber;
    private String orderAddress;
    private BigDecimal orderTotalMoney;
    private Integer orderDiscount;
    private ProductResponse productResponse;
}
