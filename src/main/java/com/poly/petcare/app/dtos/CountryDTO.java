package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountryDTO {
    private Long id;

    @NotBlank(message = "Cannot to blank field name")
    @Size(min = 3,max = 20)
    private String name;

    private List<ProductDTO> dtoList;
}
