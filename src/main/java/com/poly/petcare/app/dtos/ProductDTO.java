package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.math.BigDecimal;
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
    private Long categoryId;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field categoryID")
    private Long brandId;

    @NotNull(message = "Missing the field unit")
    private String unit;

    @NotBlank(message = "Cannot to blank field nameProduct")
    @Size(min = 3, max = 20)
    private String name;

    private String code;

    @NotBlank
    @Size(min = 5, max = 1000)
    private String descriptionLong;

    @NotBlank
    @Size(min = 3, max = 100)
    private String descriptionShort;

    private BigDecimal price;


    private String mainImage;

    private List<CategoryAttributeValueDTO> value;

    private List<Long> categoryAttributeValueID;

    private List<String> images;
}
