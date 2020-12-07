package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierResponse {
    private Long id;
    private String code;
    private String name;
    private String address;
    private String phoneNumber;
}
