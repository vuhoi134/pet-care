package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "dbo_order")
public class Order extends BaseEntity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "guid",unique = true)
    private String guid;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "total_money")
    private BigDecimal totalMoney;

    @Column(name = "status")
    private Integer status;

    @Column(name = "discount")
    private Integer discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<Transaction> transactions;

}
