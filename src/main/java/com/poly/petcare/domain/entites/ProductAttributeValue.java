package com.poly.petcare.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "dbo_product_attribute_value")
public class ProductAttributeValue {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_attribute_value_id")
    private Long categoryAttributeValueId;
}
