package com.poly.petcare.app.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseApiResult {
    private boolean isSuccess;
    private String message;
    private Long totalItem;
}
