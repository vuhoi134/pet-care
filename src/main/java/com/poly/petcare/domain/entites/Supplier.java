package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dbo_supplier")
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "supplier")
    private InputDetail inputDetail;

}
