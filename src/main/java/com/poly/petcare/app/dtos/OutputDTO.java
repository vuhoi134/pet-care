package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputDTO {
    private Long userId;
    private Long warehouseId;
    private List<OutputDetailDTO> outputDetailDTOS;
}
