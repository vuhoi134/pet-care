package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryAttributeDTO {

    private int id;

    @NotNull(message = "Missing the field categoryID")
    @PositiveOrZero(message = "Do not enter negative numbers")
    private Long categoryID;

    @NotBlank(message = "Cannot to blank field name")
    @Size(min = 3,max = 20)
    private String name;

    private List<CategoryAttributeValueDTO> value;
}
