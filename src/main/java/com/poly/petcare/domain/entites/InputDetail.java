package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "dbo_input_detail")
public class InputDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "import_price")
    private BigDecimal import_price;

    @Column(name = "theoretical_amount")
    private Long theoreticalAmount;

    @Column(name = "actual_amount")
    private Long actualAmount;

    @Column(name = "code_tag")
    private String codeTag;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "input_id")
    private Input input;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
