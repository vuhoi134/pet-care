package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
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

    @NotBlank(message = "Cannot to blank field nameProduct")
    @Size(min = 3,max = 20)
    private String name;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field Price")
    private Double price;

    @PositiveOrZero(message = "Do not enter negative numbers")
    @NotNull(message = "Missing the field Price")
    private Integer quantity;

    @NotBlank
    @Size(min = 5,max = 100)
    private String description_Long;

    @NotBlank
    @Size(min = 3,max = 50)
    private String description_Short;

    @NotBlank
    private String mainImage;

    private List<Long> categoryAttributeValueID;
}
