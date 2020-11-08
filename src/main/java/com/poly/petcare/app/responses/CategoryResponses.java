package com.poly.petcare.app.responses;

import com.poly.petcare.app.dtos.CategoryAttributeDTO;
import com.poly.petcare.app.dtos.CategoryDTO;
import com.poly.petcare.app.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponses {

    private Long id;

    private Integer level;

    private Long parentId;

    private String name;

    private String states;

//    private List<ProductResponses> productDTOList;

    private List<CategoryAttributeResponses> listAttribute;

    private List<CategoryResponses> childen;

}
