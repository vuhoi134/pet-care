package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "dbo_input")
public class Input {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "import_date")
    private Date import_date;

    @Column(name = "transporter")
    private String transporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "input")
    private List<InputDetail> inputDetails;
}