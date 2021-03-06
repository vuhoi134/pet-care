package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "dbo_input")
public class Input {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "import_date")
    private Date import_date;

    @Column(name = "status")
    private Integer status;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "transporter")
    private String transporter;

    @Column(name = "phone_transporter")
    private String phoneTransporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "input")
    private List<InputDetail> inputDetails;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}
