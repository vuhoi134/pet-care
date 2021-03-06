package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "dbo_warehouse")
public class Warehouse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private List<Input> inputs;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private List<Output> outputs;

    @OneToMany(mappedBy = "warehouse")
    private List<ProductWarehouse> productWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
