package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputResponse {
    private String code;
    private String exportDate;
    private String userName;
    private String warehouseAddress;
    private List<OutputDetailResponse> outputDetailResponses;
}
