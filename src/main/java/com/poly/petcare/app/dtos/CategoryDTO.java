package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO {
    @NotNull(message = "Missing the field categoryID")
    @Min(1)
    private Long id;

    @NotNull(message = "Missing the field level")
    @PositiveOrZero(message = "Do not enter negative numbers")
    private Integer level;

    @NotNull(message = "Missing the field parentId")
    @PositiveOrZero(message = "Do not enter negative numbers")
    private Long parentId;

    @NotBlank(message = "Cannot to blank field name")
    @Size(min = 5,max = 100)
    private String name;

    @NotBlank(message = "Cannot to blank field states")
    @Size(min = 5,max = 100)
    private String states;
    private List<ProductDTO> productDTOList;

    private List<CategoryAttributeDTO> attribute;

    private List<CategoryDTO> childen;
}
