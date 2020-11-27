package com.poly.petcare.app.responses;

import com.poly.petcare.domain.entites.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String code;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private BigDecimal totalMoney;
    private Integer status;
    private Integer discount;
    private List<OrderDetailResponse> orderDetailResponses;
}
