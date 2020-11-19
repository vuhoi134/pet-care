package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long userId;
    private Long[] transactionId;
    private String guid;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private BigDecimal totalMoney;
    private Integer discount;
    private List<OrderDetailDTO> orderDetailDTOS;
}
