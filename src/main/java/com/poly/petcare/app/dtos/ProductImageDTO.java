package com.poly.petcare.app.dtos;

import com.poly.petcare.domain.entites.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductImageDTO {
    private Long id;

    private Long productId;

    private String link;
}
