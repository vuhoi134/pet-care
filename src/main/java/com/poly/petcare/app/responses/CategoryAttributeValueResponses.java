package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryAttributeValueResponses {
    private Long id;
    private String value;
    private long categoryAttributeID;
    private String categoryAttributeName;
}
