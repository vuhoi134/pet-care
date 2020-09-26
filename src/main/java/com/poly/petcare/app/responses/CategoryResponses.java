package com.poly.petcare.app.responses;

import com.poly.petcare.app.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponses {
    private String categoryName;
    private List<ProductResponses> productList;

}
