package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "dbo_product_store")
public class ProductStore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "quantity_store")
    private Long quantityStore;

    @Column(name = "code_tag")
    private String codeTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product products;
}
