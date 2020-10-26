package com.poly.petcare.app.responses;

import com.poly.petcare.app.dtos.CategoryAttributeValueDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryAttributeResponses {
    private int id;

    private Long categoryID;

    private String name;

    private List<CategoryAttributeValueResponses> value;
}
