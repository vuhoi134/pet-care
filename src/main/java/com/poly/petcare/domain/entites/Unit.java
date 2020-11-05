package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dbo_unit")
public class Unit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "value")
    private String value;

    @OneToOne(mappedBy = "unit")
    @JsonIgnore
    private Product product;
}
