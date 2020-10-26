package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryAttributeValueDTO {

    private Long id;

    @NotBlank(message = "Cannot to blank field attribute")
    @Size(min = 3,max = 100)
    private String attribute;

    @NotBlank(message = "Cannot to blank field value")
    @Size(min = 3,max = 100)
    private String value;

    @NotNull(message = "Missing the field categoryAttributeID")
    @PositiveOrZero(message = "Do not enter negative numbers")
    private Long categoryAttributeID;
}
