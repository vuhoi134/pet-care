package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dbo_transaction")
public class Transaction extends BaseEntity{
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
