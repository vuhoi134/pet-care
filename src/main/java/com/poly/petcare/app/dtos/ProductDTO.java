package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Long id;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field categoryID")
    private Long categoryID;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field categoryID")
    private Long brandID;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field categoryID")
    private Long unitID;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field categoryID")
    private Long warehouseID;

    @NotBlank(message = "Cannot to blank field nameProduct")
    @Size(min = 3, max = 20)
    private String name;

    private String code;

    @NotBlank
    @Size(min = 5, max = 100)
    private String descriptionLong;

    @NotBlank
    @Size(min = 3, max = 50)
    private String descriptionShort;

    @NotBlank
    private String mainImage;

    private List<CategoryAttributeValueDTO> value;

    private List<Long> categoryAttributeValueID;
}
