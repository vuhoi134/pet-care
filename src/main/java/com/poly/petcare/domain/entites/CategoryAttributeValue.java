package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "dbo_category_attribute_value")
public class CategoryAttributeValue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "categoryAttributeValues")
    @JsonIgnore
    private List<Product> product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_attribute_id")
    private CategoryAttribute categoryAttribute;
}
